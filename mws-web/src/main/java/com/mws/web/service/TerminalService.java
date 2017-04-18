package com.mws.web.service;

import com.google.common.collect.Maps;
import com.mws.model.BaseDao;
import com.mws.model.Terminal;
import com.mws.model.repositories.TerminalDao;
import com.mws.service.CommonService;
import com.mws.web.common.exception.WebRequestException;
import com.mws.web.common.persistence.DynamicSpecifications;
import com.mws.web.common.persistence.SearchFilter;
import com.mws.web.common.vo.Pagination;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.HeartbeatDto;
import com.mws.web.net.dto.client.UpdateSeatClientDto;
import com.mws.web.net.service.SendService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ranfi on 3/13/16.
 */

@Service
public class TerminalService extends BaseService<Terminal, Integer> {


    @Resource
    private TerminalDao terminalDao;

    @Resource
    private CommonService commonService;

    @Resource
    private SendService sendService;

    @Override
    public void setBaseDao(BaseDao<Terminal, Integer> baseDao) {
        this.baseDao = baseDao;
        terminalDao = (TerminalDao) baseDao;
    }


    @Transactional
    public void saveTerminalForClient(Terminal terminal) {
        try {
            terminalDao.save(terminal);
        } catch (Exception e) {
            logger.error("保存终端信息异常", e);
        }

    }

    /**
     * 获取总的总段连接数
     *
     * @return
     */
    public Map<String, Long> findTerminalConnets() {
        Map<String, Long> map = Maps.newHashMap();
        Long totalTerminals = terminalDao.getTotalTerminals();
        Long disconnectedTerminals = terminalDao.getTerminalsByConnectStatus(Terminal.TerminalStatus.disconnected.value);
        Long connectedTerminals = totalTerminals.longValue() - disconnectedTerminals.longValue();
        map.put("totalTerminals", totalTerminals);
        map.put("disconnectedTerminals", disconnectedTerminals);
        map.put("connectedTerminals", connectedTerminals);
        return map;
    }

    @Transactional(readOnly = true)
    public Page<Terminal> list(Pagination pagination, Map<String, Object> params, Sort sort) {
        PageRequest pr = new PageRequest(pagination.getPage() - 1, pagination.getPageSize(), sort);
        Map<String, SearchFilter> filters = SearchFilter.parse(params);
        Specification<Terminal> spec = DynamicSpecifications.bySearchFilter(filters.values(),
                (Class<Terminal>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        Page<Terminal> ret = terminalDao.findAll(spec, pr);
        return ret;
    }


    /**
     * 根据IP查找终端信息,如果出现重复的,则删除
     *
     * @param ip
     * @return
     */
    @Transactional
    public Terminal findTerminalByIp(String ip) {
        List<Terminal> list = terminalDao.findTerminalListByIp(ip);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                terminalDao.delete(list.get(i));
            }
        }
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public Terminal findOne(int id) {
        return terminalDao.findOne(id);
    }

    @Transactional
    public void saveTerminal(String clientIp, Integer status) {
        Terminal terminal = terminalDao.findTerminalByIp(clientIp);
        if (null != terminal) {
            terminal.setConnectStatus(status);
            terminalDao.save(terminal);
        }
    }

    @Transactional
    public void saveTerminal(Terminal terminal) {
        try {
            Integer id = terminal.getId();
            //新增
            if (null == id) {
                Terminal terminal1 = terminalDao.findTerminalByIp(terminal.getIp());
                if (null != terminal1) {
                    throw new WebRequestException("ip【" + terminal.getIp() + "】已存在,请查证");
                }
                Terminal terminal2 = terminalDao.findTerminalBySeatIdAndType(terminal.getSeatId(), terminal.getTerminalType());
                if (null != terminal2) {
                    throw new WebRequestException("座位号【" + terminal.getSeatId() + "】已存在,请查证");
                }
                terminal.setConnectStatus(Terminal.TerminalStatus.unconnected.value);
            } else {
                Terminal terminal1 = terminalDao.findOne(id);
                //座位号已变更
                if (null != terminal1.getSeatId() && terminal1.getSeatId().intValue() != terminal.getSeatId().intValue()) {
                    Terminal terminal2 = terminalDao.findTerminalBySeatIdAndType(terminal.getSeatId(), terminal.getTerminalType());
                    if (null != terminal2) {
                        throw new WebRequestException("座位号【" + terminal.getSeatId() + "】已存在,请查证");
                    }
                }

                //编辑IP已变更
                if (null != terminal1.getSeatId() && !terminal1.getIp().equals(terminal.getIp())) {
                    Terminal terminal2 = terminalDao.findTerminalByIp(terminal.getIp());
                    if (null != terminal2) {
                        throw new WebRequestException("ip[" + terminal.getIp() + "]已存在,请查证");
                    }
                }
            }
            terminal.setUpdateTime(commonService.getCurrentTime());
            terminalDao.save(terminal);

            //更新到全局缓存中,以IP为准
            HeartbeatDto heartbeatDto = GlobalCache.heartbeats.get(terminal.getIp());
            if (null != heartbeatDto) {
                heartbeatDto.setSeatId(terminal.getSeatId());
                heartbeatDto.setTerminalType(terminal.getTerminalType());

                Set<String> seats = terminalDao.findTermialIpBySeatId(terminal.getSeatId());
                GlobalCache.seats.put(terminal.getSeatId(), seats);
            }

            //发送指令到终端修改
            String clientIp = terminal.getIp();//终端IP
            Integer seatId = terminal.getSeatId(); //座位编号
            UpdateSeatClientDto updateSeatClientDto = new UpdateSeatClientDto();
            updateSeatClientDto.setSeatId(seatId);
            updateSeatClientDto.setTerminalType(terminal.getTerminalType());
            sendService.sendByIp(Command.UPDATE_SEAT, Constant.commandRequestType, clientIp, updateSeatClientDto);

        } catch (WebRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("保存终端信息异常", e);
            throw new WebRequestException("保存终端出现异常");
        }

    }


    /**
     * 删除终端记录
     *
     * @param ids
     */
    @Transactional
    public void deleteTerminal(Integer[] ids) {
        Terminal terminal;
        for (Integer id : ids) {
            terminal = terminalDao.findOne(id);
            if (null == terminal) {
                throw new WebRequestException("终端记录不存在");
            }
            terminalDao.delete(id);
        }
    }

    @Transactional
    public void deleteTerminals() {
        terminalDao.deleteAll();
    }


}

package com.mws.web.net.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mws.core.mapper.BeanMapper;
import com.mws.core.mapper.JsonMapper;
import com.mws.model.*;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.CommandContent;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.*;
import com.mws.web.net.exception.MessageException;
import com.mws.web.service.AppUpgradeService;
import com.mws.web.service.PicZipService;
import com.mws.web.service.TerminalService;
import com.mws.web.service.VoteService;
import io.netty.channel.Channel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用于代表主机和代表终端的消息处理实现
 * <p/>
 * Created by ranfi on 2/22/16.
 */

@Service
@Transactional
public class MessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Resource
    private RecoveryMessageService recoveryMessageService;

    @Resource
    private AppUpgradeService appUpgradeService;

    @Resource
    private TerminalService terminalService;

    @Resource
    private PicZipService picZipService;

    @Resource
    private VoteService voteService;

    /**
     * 接收代表终端发送过来的协议数据,业务处理后并返回
     *
     * @param channel netty的连接通道会话
     * @param request
     */

    public void receive(Channel channel, MessageDto.Request request) {

        try {
            String clientIp = channel.attr(Constant.CLIENT_IP).get();
            if (null == GlobalCache.channels.get(clientIp)) {
                GlobalCache.channels.put(clientIp, channel);
            }
            String cmd = request.getCmd();
            Command command = Command.getCommand(cmd);
            MessageDto.Response response = new MessageDto.Response();
            response.setCmd(command.value);
            switch (command) {
                case UPDATE_REGISTER:
                    updateRegister(clientIp, request);
                    break;
                case HEARTBEAT: // 终端心跳包接受
                    Map<String, Object> result = doHeartbeat(clientIp, request);
                    if (MapUtils.isEmpty(result)) {
                        return;
                    }
                    response.setValues(result);
                    break;
                case VOTE:
                    terminalVote(request);
                    break;
                default:
                    break;
            }
            channel.writeAndFlush(response); // 回消息给终端
        } catch (MessageException e) {
            throw e;
        } catch (Exception e) {
            logger.error("接受消息异常", e.toString());
        }
    }

    /**
     * 代表主机主动发送命令给代表终端后，终端的返回处理
     *
     * @param channel
     * @param response
     */

    public void receive(Channel channel, MessageDto.Response response) {

        try {
            String cmd = response.getCmd();
            Command command = Command.getCommand(cmd);
            switch (command) {
                case INITIALIZE: // 终端初始化
                case HEARTBEAT: // 终端心跳
                case DOWNLOAD_PHOTO:
                case SET_SEAT:
                case SHOW_LOGO:
                case START_REGISTER:
                case SHOW_REGISTER:
                case STOP_REGISTER:
                case REGISTER: //手动补到
                case UNREGISTER: //手动销到
                case SHOW_SUBJECT:
                case START_VOTE:
                case STOP_VOTE:
                case START_SPEAK:
                case REQUEST_SPEAK:
                case SET_SPEAKING:
                case CANCEL_SPEAKING:
                    break;
                default:
                    break;
            }
            //如果S->C, C返回的指令失败
            if (null != response.getStatus() && response.getStatus().intValue() != 0) {
                String clientIp = channel.attr(Constant.CLIENT_IP).get();
                logger.error("S->C指令, client【" + clientIp + "】response error");
                //TODO 如果终端响应S端指令失败,暂时由心跳去恢复,这边就不做处理了
            }
        } catch (Exception e) {
            logger.error("接受代表主机消息处理异常", e.toString());
            throw new MessageException(e);
        }
    }

    /**
     * 接收代表终端主动发送过来的协议数据,业务处理后并返回
     * <p/>
     * <p/>
     * 当前方法用于mina处理,现已替换成netty接收
     *
     * @param session
     * @param request
     */
    @Deprecated
    public void receive(IoSession session, MessageDto.Request request) {

        try {
            String clientIp = session.getAttribute(Constant.sessionClientIp).toString();
            if (null == GlobalCache.sessions.get(clientIp)) {
                GlobalCache.sessions.put(clientIp, session);
            }
            String cmd = request.getCmd();
            Command command = Command.getCommand(cmd);
            MessageDto.Response response = new MessageDto.Response();
            response.setCmd(command.value);
            switch (command) {
                case UPDATE_REGISTER:
                    updateRegister(clientIp, request);
                    break;
                case HEARTBEAT: // 终端心跳包接受
                    Map<String, Object> result = doHeartbeat(clientIp, request);
                    if (MapUtils.isEmpty(result)) {
                        return;
                    }
                    response.setValues(result);
                    break;
                case VOTE:
                    terminalVote(request);
                    break;
                default:
                    break;
            }
            session.write(response); // 回消息给终端
        } catch (MessageException e) {
            throw e;
        } catch (Exception e) {
            logger.error("接受消息异常", e.toString());
        }
    }

    /**
     * 代表主机主动发送命令给代表终端后，终端的返回处理
     * <p/>
     * 当前方法用于mina处理,现已替换成netty接收
     *
     * @param session
     * @param response
     */
    @Deprecated
    public void receive(IoSession session, MessageDto.Response response) {

        try {
            String cmd = response.getCmd();
            Command command = Command.getCommand(cmd);
            switch (command) {
                case INITIALIZE: // 终端初始化
                    break;
                case HEARTBEAT: // 终端心跳
                    break;
                case DOWNLOAD_PHOTO:
                    break;
                case SET_SEAT:
                    break;
                case SHOW_LOGO:
                    break;
                case START_REGISTER:
                    break;
                case SHOW_REGISTER:
                    break;
                case STOP_REGISTER:
                    break;
                case REGISTER: // 手动补到
                    break;
                case UNREGISTER: // 手动销到
                    break;
                case SHOW_SUBJECT:
                    break;
                case START_VOTE:
                    break;
                case STOP_VOTE:
                    break;
                case START_SPEAK:
                    break;
                case REQUEST_SPEAK:
                    break;
                case SET_SPEAKING:
                    break;
                case CANCEL_SPEAKING:
                    break;
                default:
                    break;
            }
            //如果S->C, C返回的指令失败
            if (null != response.getStatus() && response.getStatus().intValue() != 0) {
                String clientIp = session.getAttribute(Constant.sessionClientIp).toString();
                logger.error("S->C指令, client【" + clientIp + "】response error");
                //TODO 如果终端响应S端指令失败,暂时由心跳去恢复, 这边就不做处理了
            }
        } catch (Exception e) {
            logger.error("接受代表主机消息处理异常", e.toString());
            throw new MessageException(e);
        }
    }

    /**
     * 终端机器上传报到状态
     *
     * @param clientIp
     * @param request
     */
    public void updateRegister(String clientIp, MessageDto.Request request) {
        try {
            Map<String, Object> params = (Map<String, Object>) request.getParams();
            if (null != params && !params.isEmpty()) {
                // 座位号
                int seatId = Integer.parseInt(params.get("seatId").toString());
                // 上传报到的状态 1:已报到 0: 未报到
                int status = Integer.parseInt(params.get("status").toString());

                HeartbeatDto heartbeatDto = GlobalCache.heartbeats.get(clientIp);
                heartbeatDto.setRegister(status);

                GlobalCache.heartbeats.put(clientIp, heartbeatDto);

                if (heartbeatDto.getSeatId().intValue() != seatId) {
                    logger.error("上传报到状态时发现座位号不一致,终端座位号【" + seatId + "】,代表主机座位号【" + heartbeatDto.getSeatId() + "】");
                    return;
                }
                // 更新到数据库
                Terminal terminal = terminalService.findTerminalByIp(clientIp);
                terminal.setRegister(status);
                terminalService.saveTerminalForClient(terminal);
            }
        } catch (Exception e) {
            logger.error("上传报到信息异常", e);
        }
    }

    /**
     * 终端机器上传表决信息
     *
     * @param request
     */
    public void terminalVote(MessageDto.Request request) {
        try {
            VoteDto voteDto = BeanMapper.map(request.getParams(), VoteDto.class);
            if (null == voteDto) {
                return;
            }
            Vote vote = voteService.findVoteBySeatId(voteDto.getSeatId());
            if (null == vote) {
                vote = new Vote();
            }
            vote.setSeatId(voteDto.getSeatId());
            vote.setType(voteDto.getType());
            //获取已表决的结果集
            List<VoteResult> voteResults = vote.getVotes();

            //构造新的表决结果集
            List<VoteResult> newVoteResults = Lists.newArrayList();
            // 设置表决项
            for (VoteItemDto item : voteDto.getVotes()) {
                Integer subjectId = item.getId();  //表决议题的ID
                if (null == subjectId) {
                    continue;
                }
                VoteResult voteResult = getVoteResult(voteResults, subjectId);
                if (null == voteResult) {
                    voteResult = new VoteResult();
                }
                voteResult.setVote(vote);
                voteResult.setSubjectId(subjectId); // 表决议题的ID，从1开始由客户端传入
                voteResult.setSubject(item.getItem());
                voteResult.setValue(item.getValue());
                newVoteResults.add(voteResult);
            }
            vote.setVotes(newVoteResults);
            voteService.saveVote(vote);
        } catch (Exception e) {
            logger.error("解析终端上传表决信息异常", e);
        }
    }

    /**
     * 校验是否已经表决过, 如果是则从数据库取出
     *
     * @param voteResults 已表决的结果集
     * @param subjectId   表决子议题ID
     * @return
     */
    private VoteResult getVoteResult(List<VoteResult> voteResults, int subjectId) {
        if (CollectionUtils.isEmpty(voteResults)) {
            return null;
        }
        for (VoteResult voteResult : voteResults) {
            if (voteResult.getSubjectId().intValue() == subjectId) {
                return voteResult;
            }
        }
        return null;
    }

    /**
     * 当终端机器关闭或者异常时，则断开session并实时更新到数据库
     * <p/>
     * 适用mina
     *
     * @param clientIp
     */
    @Deprecated
    public void closeTerminalSession(String clientIp) {
        try {

            Terminal terminal = terminalService.findTerminalByIp(clientIp);
            if (null != terminal) {
                terminal.setConnectStatus(TerminalDto.TerminalStatus.disconnected.value);
                terminalService.saveTerminal(terminal);
            }
            // 清除服务器全局的会话
            GlobalCache.closeSession(clientIp);

        } catch (Exception e) {
            logger.error("关闭终端session异常", e);
        }

    }

    /**
     * 当终端机器关闭或者异常时，则断开session并实时更新到数据库 适用netty
     *
     * @param clientIp
     */
    @Transactional
    public void closeTerminalChannel(String clientIp) {
        try {
            terminalService.saveTerminal(clientIp, Terminal.TerminalStatus.disconnected.value);
            // 清除服务器全局的会话
            GlobalCache.closeChannel(clientIp);

        } catch (Exception e) {
            logger.error("关闭终端session异常", e);
        }

    }

    /**
     * 持久化终端数据
     *
     * @param clientIp
     * @param heartbeatDto
     */
    @Transactional
    public HeartbeatDto saveTerminal(String clientIp, HeartbeatDto heartbeatDto) {
        try {
            Terminal terminal = terminalService.findTerminalByIp(clientIp);
            if (null == terminal) {
                terminal = new Terminal();
            }
            Channel channel = GlobalCache.channels.get(clientIp);
            int connectStatus;
            if (null != channel && channel.isWritable()) {
                connectStatus = TerminalDto.TerminalStatus.connected.value;
            } else {
                connectStatus = TerminalDto.TerminalStatus.disconnected.value;
            }
            heartbeatDto = GlobalCache.updateHeartbeats(clientIp, heartbeatDto); // 保存终端最新的心跳
            heartbeatDto.setConnectStatus(connectStatus); // 设置连接状态

            BeanMapper.copy(heartbeatDto, terminal);
            terminalService.saveTerminalForClient(terminal);

        } catch (Exception e) {
            logger.error("终端[" + clientIp + "]更新心跳包出现异常", e);
        }
        return heartbeatDto;
    }


    /**
     * 检测代表终端和代表主机的消息是否一致性
     * <p/>
     * 如果是列席终端, 只要考虑到设置座位指令即可
     *
     * @param heartbeatDto
     */
    @Deprecated
    private boolean checkClientMessageConsistency(HeartbeatDto heartbeatDto) {
        int status = heartbeatDto.getStatus().intValue();
        //如果是正式终端
        if (heartbeatDto.getTerminalType().intValue() == Terminal.TerminalType.NORMAL.value) {
            CommandContent commandContent = GlobalCache.getLatestCommandContent();
            // 如果是代表主机和终端状态不同步，则发送代表主机最新的命令给终端

            if (null == commandContent) {
                return true;
            }
            Command cmd = commandContent.getCmd();
            if (cmd.status > status) {
                recoveryMessageService.sendMessageByCommand(cmd, commandContent.getContent(), heartbeatDto.getIp());
                return false;
            }
        } else {
            //TODO 列席终端暂时不恢复?
        }
        return true;
    }


    /**
     * 检测终端指令是否和代表主机一致,如果不一致则重新发送恢复指令,最大重试次数为5次
     *
     * @param heartbeatDto
     */
    private boolean checkTerminalCommandConsistency(HeartbeatDto heartbeatDto) {
        if (heartbeatDto.getLatestCommandStatus() > heartbeatDto.getStatus() && heartbeatDto.getRetryCount() <= GlobalCache.MAX_RETRY_COUNT) {
            CommandContent commandContent = GlobalCache.getLatestCommandContentByStatus(heartbeatDto.getLatestCommandStatus());
            if (null == commandContent) {
                return true;
            }

            heartbeatDto.setRetryCount(heartbeatDto.getRetryCount() + 1);
            GlobalCache.heartbeats.put(heartbeatDto.getIp(), heartbeatDto);

            recoveryMessageService.sendMessageByCommand(commandContent.getCmd(), commandContent.getContent(), heartbeatDto.getIp());
            return false;
        }
        return true;
    }

    /**
     * 代表主机处理终端的心跳业务逻辑
     *
     * @param clientIp
     * @param request
     * @return
     */
    public Map<String, Object> doHeartbeat(String clientIp, MessageDto.Request request) {
        try {
            HeartbeatDto heartbeatDto = BeanMapper.map(request.getParams(), HeartbeatDto.class);
            heartbeatDto = saveTerminal(clientIp, heartbeatDto);

            logger.info("心跳包新:"+ JsonMapper.nonDefaultMapper().toJson(heartbeatDto));

            //如果不一致,则单独发送恢复指令
            if (!checkTerminalCommandConsistency(heartbeatDto)) {
                return null;
            }

            // 检测服务器的一些更新操作,通过终端心跳返回
            Map<String, Object> values = Maps.newHashMap();
            values.put("seatId", heartbeatDto.getSeatId());
            values.put("terminalType", heartbeatDto.getTerminalType());

            AppUpgrade appUpgrade = appUpgradeService.getLatestAppUpgrade();
            if (null != appUpgrade && appUpgrade.getVersionCode() > heartbeatDto.getVersionCode()) {
                values.put("versionCode", appUpgrade.getVersionCode());
                values.put("downloadUrl", appUpgrade.getUrl());
            }

            // 检测终端照片资源是否和服务器一致，否则返回下载图片的地址
            PicZip picZip = picZipService.getLatestPicZip();
            boolean notDownloaded = (null == heartbeatDto.getPicZipMd5() || "".equals(heartbeatDto.getPicZipMd5()));
            if (null != picZip && (notDownloaded || !picZip.getMd5().equalsIgnoreCase(heartbeatDto.getPicZipMd5()))) {
                values.put("picZipUrl", picZip.getUrl());
                values.put("picZipMd5", picZip.getMd5());
            }
            return values;

        } catch (Exception e) {
            logger.error("处理终端心跳包异常", e);
            throw new MessageException(e);
        }
    }
}

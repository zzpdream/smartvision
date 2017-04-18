package com.mws.web.net.service;

import com.mws.core.mapper.JsonMapper;
import com.mws.core.utils.Reflections;
import com.mws.model.Terminal;
import com.mws.model.repositories.TerminalDao;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.HeartbeatDto;
import com.mws.web.net.dto.MessageDto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ranfi on 3/23/16.
 */
@Service
public class SendService {

    private static Logger logger = LoggerFactory.getLogger(SendService.class);
    private Lock lock = new ReentrantLock();


    @Autowired
    private TerminalDao terminalDao;


    /**
     * 更新终端状态
     *
     * @param clientIp
     * @param status
     */
    @Transactional
    public void updateTerminalStatus(String clientIp, int status) {
        try {
            //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
            GlobalCache.updateHeartbeats(clientIp, status);

            Terminal terminal = terminalDao.findTerminalByIp(clientIp);
            if (null != terminal) {
                terminal.setLatestCommandStatus(status);
                terminalDao.save(terminal);
            }
        } catch (Exception e) {
            logger.error("更新终端[" + clientIp + "]状态出现异常", e);
        }
    }

    /**
     * 下发消息到指定的终端
     *
     * @param request  消息请求对象
     * @param seatId   座位编号
     * @param clientIp 终端座位号
     */
    public void send(MessageDto.Request request, int seatId, String clientIp, boolean... isUpdateStatus) {
        Channel channel = GlobalCache.channels.get(clientIp);
        if (!channel.isActive()) {
            logger.error("代表终端【" + clientIp + "】已断开");
            return;
        }
        Object obj = request.getParams();
        if (obj instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) obj;
            params.put("seatId", seatId);
        }

        if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
            //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
            updateTerminalStatus(clientIp, Command.getCommand(request.getCmd()).status);
        }

        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {

                }
            }
        });
    }


    /**
     * 发送指令
     *
     * @param cmd
     * @param type
     * @param seatId
     * @param param
     */
    public boolean send(Command cmd, String type, int seatId, Object param, boolean... isUpdateStatus) {
        Set<String> clientIps = GlobalCache.seats.get(seatId);
        if (null == clientIps || clientIps.size() == 0) {
            return false;
        }
        MessageDto.Request request = new MessageDto.Request();
        request.setCmd(cmd.value);
        request.setType(type);
        for (String clientIp : clientIps) {

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(clientIp, cmd.status);
            }

            Channel channel = GlobalCache.channels.get(clientIp);
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + clientIp + "】已断开");
                continue;
            }

            Reflections.setFieldValue(param, "seatId", seatId);
            request.setParams(param);
            logger.info("终端[" + clientIp + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));

            channel.writeAndFlush(request);
        }
        return true;
    }

    /**
     * @param cmd
     * @param type
     * @param clientIp
     * @param param
     */
    public boolean sendByIp(Command cmd, String type, String clientIp, Object param, boolean... isUpdateStatus) {
        Channel channel = GlobalCache.channels.get(clientIp);
        if (StringUtils.isBlank(clientIp)) {
            return false;
        }
        MessageDto.Request request = new MessageDto.Request();
        request.setCmd(cmd.value);
        request.setType(type);
        request.setParams(param);

        if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
            //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
            updateTerminalStatus(clientIp, cmd.status);
        }

        if (null == channel || !channel.isWritable()) {
            logger.error("代表终端【" + clientIp + "】已断开");
            return false;
        }
        logger.info("终端[" + clientIp + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
        channel.writeAndFlush(request);
        return true;
    }


    /**
     * 仅对排了座位的终端发送指令
     *
     * @param cmd
     * @param type
     * @param seatId
     * @param param
     */
    public void sendForSeat(Command cmd, String type, int seatId, Object param, boolean... isUpdateStatus) {
        Set<String> clientIps = GlobalCache.seats.get(seatId);
        if (null == clientIps || clientIps.size() == 0) {
            return;
        }
        MessageDto.Request request = new MessageDto.Request();
        request.setCmd(cmd.value);
        request.setType(type);
        for (String clientIp : clientIps) {

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(clientIp, cmd.status);
            }

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(clientIp);
            if (null == heartbeat.getMemberId() || heartbeat.getMemberId().intValue() <= 0) {
                continue;
            }

            Channel channel = GlobalCache.channels.get(clientIp);
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + clientIp + "】已断开");
                continue;
            }

            Reflections.setFieldValue(param, "seatId", seatId);
            request.setParams(param);

            logger.info("终端[" + clientIp + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
        }
    }


    /**
     * 给单个终端发送指令,必须有表决权
     *
     * @param cmd
     * @param type
     * @param seatId
     * @param param
     */
    public boolean sendForVotingRights(Command cmd, String type, int seatId, Object param, boolean... isUpdateStatus) {
        boolean isSuccess = false;
        Set<String> clientIps = GlobalCache.seats.get(seatId);
        if (null == clientIps || clientIps.size() == 0) {
            return false;
        }
        MessageDto.Request request = new MessageDto.Request();
        request.setCmd(cmd.value);
        request.setType(type);
        for (String clientIp : clientIps) {

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(clientIp);

            //如果未排座,则服务器不执行下发
            if (null == heartbeat.getMemberId() || heartbeat.getMemberId().intValue() <= 0) {
                continue;
            }

            //没有表决权则不发送指令
            if (null == heartbeat.getVotingRights() || heartbeat.getVotingRights().intValue() == 0) {
                continue;
            }
            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {

                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(clientIp, cmd.status);
            }

            Channel channel = GlobalCache.channels.get(clientIp);
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + clientIp + "】已断开");
                continue;
            }

            Reflections.setFieldValue(param, "seatId", seatId);
            request.setParams(param);
            logger.info("终端[" + clientIp + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 给单个终端发送指令,必须有表决权,并且报名成功
     *
     * @param cmd
     * @param type
     * @param seatId
     * @param param
     */
    public void sendForVotingRightsAndRegisted(Command cmd, String type, int seatId, Object param, boolean... isUpdateStatus) {
        Set<String> clientIps = GlobalCache.seats.get(seatId);
        if (null == clientIps || clientIps.size() == 0) {
            return;
        }
        MessageDto.Request request = new MessageDto.Request();
        request.setCmd(cmd.value);
        request.setType(type);
        for (String clientIp : clientIps) {

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(clientIp);

            //如果未排座,则服务器不执行下发
            if (null == heartbeat.getMemberId() || heartbeat.getMemberId().intValue() <= 0) {
                continue;
            }

            //没有表决权则不发送指令
            if (null == heartbeat.getVotingRights() || heartbeat.getVotingRights().intValue() == 0) {
                continue;
            }
            //没有报名则不发送指令
            if (null == heartbeat.getRegister() || heartbeat.getRegister().intValue() == 0) {
                continue;
            }

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(clientIp, cmd.status);
            }

            Channel channel = GlobalCache.channels.get(clientIp);
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + clientIp + "】已断开");
                continue;
            }

            Reflections.setFieldValue(param, "seatId", seatId);
            request.setParams(param);
            logger.info("终端[" + clientIp + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
        }
    }

    /**
     * 主控机发送指令给代表主机后,对所有代表终端进行广播
     *
     * @param cmd   命令
     * @param type  请求类型
     * @param param 请求参数
     */
    public void broadcast(Command cmd, String type, Object param, boolean... isUpdateStatus) {
        lock.lock();
        try {
            for (Map.Entry<String, Channel> entry : GlobalCache.channels.entrySet()) {

                HeartbeatDto heartbeat = GlobalCache.heartbeats.get(entry.getKey());

                MessageDto.Request request = new MessageDto.Request();
                request.setCmd(cmd.value);
                request.setType(type);
                Reflections.setFieldValue(param, "seatId", heartbeat.getSeatId());
                request.setParams(param);

                if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                    //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                    updateTerminalStatus(entry.getKey(), cmd.status);
                }

                Channel channel = entry.getValue();
                if (null == channel || !channel.isWritable()) {
                    logger.error("代表终端【" + entry.getKey() + "】已断开");
                    continue;
                }

                logger.info("终端[" + entry.getKey() + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
                channel.writeAndFlush(request);
            }
        } finally {
            lock.unlock();
        }

    }

    /**
     * 主控机发送指令给代表主机后,对所有代表终端进行广播
     * <p/>
     * 只针对排座人员的终端
     *
     * @param cmd   消息命令
     * @param type  请求类型
     * @param param 发送参数
     */
    public void broadcastForSeat(Command cmd, String type, Object param, boolean... isUpdateStatus) {
        for (Map.Entry<String, Channel> entry : GlobalCache.channels.entrySet()) {

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(entry.getKey());
            //如果未排座,则服务器不执行下发
            if (null == heartbeat.getMemberId() || heartbeat.getMemberId().intValue() <= 0) {
                continue;
            }
            // 如果是列席终端, 则服务器不执行下发
//            if (heartbeat.getTerminalType().intValue() == Terminal.TerminalType.ATTEND.value) {
//                continue;
//            }

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(entry.getKey(), cmd.status);
            }

            Channel channel = entry.getValue();
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + entry.getKey() + "】已断开");
                continue;
            }

            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(cmd.value);
            request.setType(type);
//            Reflections.setFieldValue(param, "seatId", heartbeat.getSeatId());
            request.setParams(param);

            logger.info("终端[" + entry.getKey() + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
        }
    }


    /**
     * 主控机发送指令给代表主机后,对所有拥有表决权的代表终端进行广播
     *
     * @param cmd   消息命令
     * @param type  请求类型
     * @param param 发送参数
     */
    public void broadcastForVotingRights(Command cmd, String type, Object param, boolean... isUpdateStatus) {
        for (Map.Entry<String, Channel> entry : GlobalCache.channels.entrySet()) {

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(entry.getKey());

            //如果未排座,则服务器不执行下发
            if (null == heartbeat.getMemberId() || heartbeat.getMemberId().intValue() <= 0) {
                continue;
            }

            //没有表决权则不发送指令
            if (null == heartbeat.getVotingRights() || heartbeat.getVotingRights().intValue() == 0) {
                continue;
            }

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(entry.getKey(), cmd.status);
            }

            Channel channel = entry.getValue();
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + entry.getKey() + "】已断开");
                continue;
            }

            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(cmd.value);
            request.setType(type);
            Reflections.setFieldValue(param, "seatId", heartbeat.getSeatId());
            request.setParams(param);

            logger.info("终端[" + entry.getKey() + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
        }
    }


    /**
     * 主控机发送指令给代表主机后,对所有拥有表决权并且已报到的代表终端进行广播
     *
     * @param cmd   消息命令
     * @param type  请求类型
     * @param param 发送参数
     */
    public void broadcastForVotingRightsAndRegisted(Command cmd, String type, Object param, boolean... isUpdateStatus) {
        for (Map.Entry<String, Channel> entry : GlobalCache.channels.entrySet()) {

            HeartbeatDto heartbeat = GlobalCache.heartbeats.get(entry.getKey());
            //没有表决权则不发送指令
            if (null == heartbeat.getVotingRights() || heartbeat.getVotingRights().intValue() == 0) {
                continue;
            }

            //没有报名则不发送指令
            if (null == heartbeat.getRegister() || heartbeat.getRegister().intValue() == 0) {
                continue;
            }

            if (isUpdateStatus.length == 0 || isUpdateStatus[0]) {
                //更新终端的指令状态到心跳包, 便于终端重启\断点等异常恢复
                updateTerminalStatus(entry.getKey(), cmd.status);
            }

            Channel channel = entry.getValue();
            if (null == channel || !channel.isWritable()) {
                logger.error("代表终端【" + entry.getKey() + "】已断开");
                continue;
            }

            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(cmd.value);
            request.setType(type);
            Reflections.setFieldValue(param, "seatId", heartbeat.getSeatId());
            request.setParams(param);

            logger.info("终端[" + entry.getKey() + "]发送指令:" + JsonMapper.nonDefaultMapper().toJson(request));
            channel.writeAndFlush(request);
        }
    }

}

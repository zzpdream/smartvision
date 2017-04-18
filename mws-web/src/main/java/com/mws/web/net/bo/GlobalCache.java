package com.mws.web.net.bo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mws.web.net.dto.HeartbeatDto;
import io.netty.channel.Channel;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局上下文实现类,用于存储终端连接的相关信息，服务器重启丢失
 * <p/>
 * Created by ranfi on 2/23/16.
 */
public class GlobalCache {

    private static Logger logger = LoggerFactory.getLogger(GlobalCache.class);


    /**
     * 终端指令最大恢复次数
     */
    public static int MAX_RETRY_COUNT = 5;


    /**
     * 存储终端座位号和终端IP的对应关系,一个座位号可能存在多个IP
     * key : 终端座位号
     * value : 终端IP集合
     */
    public static final Map<Integer, Set<String>> seats = Maps.newConcurrentMap();


    /**
     * 存储终端的TCP连接全局会话
     * <p/>
     * key : 终端IP,作为终端的唯一标识
     * value : 终端的channel会话通道
     */
    public static final Map<String, Channel> channels = Maps.newConcurrentMap();

    /**
     * 存储终端的全局会话,仅适用mina框架,目前已替换成netty,请适用channels全局通道
     * <p/>
     * key : 终端IP,作为终端的唯一标识
     * value : 终端的session会话
     */
    @Deprecated
    public static final Map<String, IoSession> sessions = Maps.newConcurrentMap();


    /**
     * 存储所有终端的最新心跳信息
     * <p/>
     * key : 终端IP
     * value : 终端最新的心跳包
     */
    public static final Map<String, HeartbeatDto> heartbeats = Maps.newConcurrentMap();


    /**
     * 存储所有主控制机下发的指令内容
     * <p/>
     * key : 当前下发的指令状态
     * value : 下发的指令内容
     */
    public static final List<CommandContent> contents = Lists.newLinkedList();


    /**
     * 系统初始化时清理内存
     */
    public static void clearMemory() {
        GlobalCache.channels.clear();
        GlobalCache.heartbeats.clear();
        GlobalCache.contents.clear();
        GlobalCache.seats.clear();
    }

    /**
     * 初始化心跳,恢复到系统开机的状态
     */
    public static void initHeartbeats() {
        if (null == GlobalCache.heartbeats || GlobalCache.heartbeats.isEmpty()) {
            return;
        }
        for (Map.Entry<String, HeartbeatDto> entry : GlobalCache.heartbeats.entrySet()) {
            HeartbeatDto heartbeatDto = entry.getValue();
            heartbeatDto.setStatus(Command.INITIALIZE.status);
            heartbeatDto.setLatestCommandStatus(Command.INITIALIZE.status);
            heartbeatDto.setRegister(0);
        }
    }

    /**
     * 终端断开后的处理,暂时不做任何逻辑
     *
     * @param clientIp 终端IP
     */
    public static void closeSession(String clientIp) {
        IoSession session = GlobalCache.sessions.get(clientIp);
        if (null != session) {
            session.closeOnFlush();
        }
        GlobalCache.sessions.remove(clientIp);
    }

    /**
     * 终端断开后的处理,暂时不做任何逻辑
     *
     * @param clientIp 终端IP
     */
    public static void closeChannel(String clientIp) {
        //TODO 自己程序不做处理,交给netty内部去管理
//        Channel channel = GlobalCache.channels.get(clientIp);
//        if (null != channel) {
//            channel.close();
//        }
        GlobalCache.channels.remove(clientIp);
    }

    /**
     * 更新终端心跳包
     *
     * @param clientIp             终端IP
     * @param terminalHeartbeatDto 终端上传的心跳包
     */
    public static HeartbeatDto updateHeartbeats(String clientIp, HeartbeatDto terminalHeartbeatDto) {

        //如果心跳不存在,则直接用终端心跳保存
        HeartbeatDto heartbeatDto = GlobalCache.heartbeats.get(clientIp);
        if (null == heartbeatDto) {
            heartbeatDto = new HeartbeatDto();
            heartbeatDto.setLatestCommandStatus(terminalHeartbeatDto.getStatus());
            heartbeatDto.setRetryCount(0);
        }

        heartbeatDto.setUpdateTime(new Timestamp(System.currentTimeMillis())); //设置最新的心跳时间
        heartbeatDto.setStatus(terminalHeartbeatDto.getStatus());  //更新终端业务状态
//        heartbeatDto.setRegister(terminalHeartbeatDto.getRegister());  //更新终端报名状态
        heartbeatDto.setAppVersion(terminalHeartbeatDto.getAppVersion());
        heartbeatDto.setVersionCode(terminalHeartbeatDto.getVersionCode());
        heartbeatDto.setIp(clientIp);
        heartbeatDto.setPicZipMd5(terminalHeartbeatDto.getPicZipMd5());
        heartbeatDto.setMemberId(terminalHeartbeatDto.getMemberId());
        heartbeatDto.setSeatId(terminalHeartbeatDto.getSeatId());
        heartbeatDto.setVotingRights(terminalHeartbeatDto.getVotingRights());
        heartbeatDto.setTerminalType(terminalHeartbeatDto.getTerminalType());

        GlobalCache.heartbeats.put(clientIp, heartbeatDto);   //重新写回心跳hash表

        Set<String> seats = GlobalCache.seats.get(heartbeatDto.getSeatId());
        if (null != seats && seats.size() > 0) {
            if (!seats.contains(clientIp)) {
                seats.add(clientIp);
            }
            //如果同一个座位号有多个IP时,则先清除掉, 5秒后恢复
            if (seats.size() > 2) {
                seats.clear();
                logger.error("座位号[" + heartbeatDto.getSeatId() + "]存在多个IP");
            }
        } else {
            seats = Sets.newHashSet();
            seats.add(clientIp);
            GlobalCache.seats.put(heartbeatDto.getSeatId(), seats);
        }
        return heartbeatDto;
    }

    /**
     * 更新心跳包报名状态
     *
     * @param seatId         座位号
     * @param registerStatus 报名状态 1:已报名  0:未报名
     */
    public static void updateHeartbeats(Integer seatId, Integer registerStatus) {
        Set<String> clientIps = GlobalCache.seats.get(seatId);
        if (null == clientIps || clientIps.size() == 0) {
            return;
        }
        HeartbeatDto heartbeatDto;
        for (String ip : clientIps) {
            heartbeatDto = GlobalCache.heartbeats.get(ip);
            heartbeatDto.setRegister(registerStatus);
        }
    }

    /**
     * 更新终端心跳包最新业务状态
     *
     * @param clientIp            终端IP
     * @param latestCommandStatus 主控机下发的最新指令状态
     */
    public static void updateHeartbeats(String clientIp, Integer latestCommandStatus) {
        HeartbeatDto heartbeatDto = GlobalCache.heartbeats.get(clientIp);
        if (null != heartbeatDto) {
            heartbeatDto.setLatestCommandStatus(latestCommandStatus);
            heartbeatDto.setRetryCount(0);
        }
    }


    /**
     * 添加当前控制机下发的指令内容
     *
     * @param content
     */
    public static void addMeetingContents(Command cmd, Object content) {
        CommandContent commandContent = new CommandContent();
        commandContent.setCmd(cmd);
        commandContent.setContent(content);
        contents.add(commandContent);
    }


    /**
     * 获取主控机下发的最新的内容
     *
     * @return
     */
    public static synchronized CommandContent getLatestCommandContent() {
        if (contents.size() > 0) {
            return contents.get(contents.size() - 1);
        }
        return null;
    }


    /**
     * 根据指令状态获取队列当中主控下发的指令
     *
     * @param status 指令的状态
     * @return
     */
    public static CommandContent getLatestCommandContentByStatus(int status) {
        if (contents.size() == 0) {
            return null;
        }
        for (int i = contents.size() - 1; i > 0; i--) {
            if (contents.get(i).getCmd().status == status) {
                return contents.get(i);
            }
        }
        return null;
    }
}

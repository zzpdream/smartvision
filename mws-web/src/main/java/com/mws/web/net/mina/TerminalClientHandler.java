package com.mws.web.net.mina;

import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.dto.HeartbeatDto;
import com.mws.web.net.dto.MessageDto;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by ranfi on 2/22/16.
 */
public class TerminalClientHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        // TODO Auto-generated method stub
        //super.messageReceived(session, message);
        System.out.println("Client receive message :" + message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        super.sessionIdle(session, status);
        // 如果IoSession闲置，则关闭连接
        if (status == IdleStatus.BOTH_IDLE) {
            MessageDto.Request request = new MessageDto.Request();
            request.setCmd(Command.HEARTBEAT.value);
            HeartbeatDto heartbeat = new HeartbeatDto();
            heartbeat.setRegister(0);
            heartbeat.setSeatId(1);
            heartbeat.setTerminalType(Constant.TerminalType.standard.status);
            heartbeat.setVersionCode(9);
            heartbeat.setAppVersion("V1.1.0");
            heartbeat.setStatus(20);
            heartbeat.setPicZipMd5("4117e04eaf192c40708c40921e75980f");
            heartbeat.setPicZipUrl("ddd");
            request.setParams(heartbeat);
            String json = JsonMapper.nonDefaultMapper().toJson(request);
            session.write(json);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("Client send message：" + message.toString());
        //super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        // 设置IoSession闲置时间，参数单位是秒
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }


}

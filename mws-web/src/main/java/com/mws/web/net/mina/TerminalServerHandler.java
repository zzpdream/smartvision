package com.mws.web.net.mina;

import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.MessageDto;
import com.mws.web.net.exception.MessageException;
import com.mws.web.net.service.MessageService;

@Service
public class TerminalServerHandler extends IoHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(TerminalServerHandler.class);


    @Autowired
    private MessageService messageService;

    public TerminalServerHandler() {
        logger.info("nio TCP启动");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.warn("Unexpected exception. " + cause);
        String clientIp = session.getAttribute(Constant.sessionClientIp).toString();
        messageService.closeTerminalSession(clientIp);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String clientIp = session.getAttribute(Constant.sessionClientIp).toString();
        logger.info("[" + clientIp + "] host receive data:" + message.toString());
        try {
            Map<String, Object> result = JsonMapper.nonEmptyMapper().fromJson(message.toString(), Map.class);
            String type = (String) result.get("type");
            if (null != type && type.equalsIgnoreCase(Constant.commandResponseType)) {
                MessageDto.Response response = JsonMapper.nonEmptyMapper().fromJson(message.toString(), MessageDto.Response.class);
                messageService.receive(session, response);
            } else {
                MessageDto.Request request = JsonMapper.nonEmptyMapper().fromJson(message.toString(), MessageDto.Request.class);
                messageService.receive(session, request);
            }
        } catch (MessageException e) {
            //TODO 消息回复处理
        } catch (Exception e) {
            logger.error("[" + clientIp + "] host unknown error");
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {

        String msg;
        if (message instanceof MessageDto.Request || message instanceof MessageDto.Response) {
            msg = JsonMapper.nonDefaultMapper().toJson(message);
        } else {
            msg = message.toString();
        }
        logger.info("send data:" + msg);
        super.messageSent(session, msg);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("there is a session closed");
        String clientIp = session.getAttribute(Constant.sessionClientIp).toString();
        messageService.closeTerminalSession(clientIp);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("there is a session created");
        String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        session.setAttribute(Constant.sessionClientIp, clientIP);

        GlobalCache.sessions.put(clientIP, session);  //存储终端连接会话
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus arg1)
            throws Exception {
        logger.info(session.getId() + "(SesssionID) is idle in the satate-->" + arg1);

    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {


    }


    /**
     * 消息广播到所有存活的终端机器
     *
     * @param message
     */
    public void broadcast(String message) {
        synchronized (GlobalCache.sessions) {
            for (Map.Entry<String, IoSession> entry : GlobalCache.sessions.entrySet()) {
                IoSession session = entry.getValue();
                if (session.isConnected()) {
                    session.write("BROADCAST OK " + message);
                }
            }
        }
    }
}

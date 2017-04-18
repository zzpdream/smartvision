package com.mws.web.net.netty;

import com.mws.core.context.SpringContextHolder;
import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.bo.GlobalCache;
import com.mws.web.net.dto.MessageDto;
import com.mws.web.net.exception.MessageException;
import com.mws.web.net.service.MessageService;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 会议终端TCP处理器
 *
 * @author ranfi
 */

@ChannelHandler.Sharable
public class ConferenceHandler extends ChannelHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(ConferenceHandler.class);

    public ConferenceHandler() {
        logger.info("netty nio TCP启动");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String clientIp = ctx.channel().attr(Constant.CLIENT_IP).get();
        logger.error("Client ip [" + clientIp + "] has active");
        GlobalCache.channels.put(clientIp, ctx.channel()); // 存储终端连接会话
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        String clientIp = ctx.channel().attr(Constant.CLIENT_IP).get();
//        logger.info("Reading data of the [" + clientIp + "] client: " + msg.toString());
        try {
            String message = msg.toString();
            MessageService messageService = SpringContextHolder.getBean("messageService");
            if (null != message && message.contains("\"type\":\"res\"")) {
                MessageDto.Response response = JsonMapper.nonEmptyMapper().fromJson(message, MessageDto.Response.class);
                messageService.receive(ctx.channel(), response);
            } else {
                MessageDto.Request request = JsonMapper.nonEmptyMapper().fromJson(message, MessageDto.Request.class);
                messageService.receive(ctx.channel(), request);
            }
        } catch (MessageException e) {
            //TODO 消息回复处理
            logger.error("read client data has an exception", e);
        } catch (Exception e) {
            logger.error("[" + clientIp + "] host unknown error");
        }
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise)
            throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
//        String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
//        logger.info("there is a client connected. ip:" + clientIP);
//        ctx.attr(Constant.CLIENT_IP).set(clientIP);
//        GlobalCache.channels.put(clientIP, ctx.channel()); // 存储终端连接会话

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        logger.info("there is a client Registered. ip:" + clientIP);
        ctx.attr(Constant.CLIENT_IP).set(clientIP);
        GlobalCache.channels.put(clientIP, ctx.channel()); // 存储终端连接会话
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelUnregistered(ctx);
    }


    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        logger.error("Client ip [" + clientIP + "] has inactive");
        MessageService messageService = SpringContextHolder.getBean("messageService");
        messageService.closeTerminalChannel(clientIP);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
            throws Exception {
        super.disconnect(ctx, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.error("Unexpected exception. " + cause);
        String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        MessageService messageService = SpringContextHolder.getBean("messageService");
        messageService.closeTerminalChannel(clientIP);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        // 如果Channel读取数据闲置，则关闭连接
        if (event.state() == IdleState.READER_IDLE) {
            String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
            logger.error("Client [" + clientIP + "] has idle");
            MessageService messageService = SpringContextHolder.getBean("messageService");
            messageService.closeTerminalChannel(clientIP);
            ctx.channel().close();
        }
    }

}

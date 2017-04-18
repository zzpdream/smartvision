package com.mws.web.net.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Command;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.dto.HeartbeatDto;
import com.mws.web.net.dto.MessageDto;

/**
 * 客户端处理器
 * 
 * @author ranfi
 *
 */
public class ClientHandler extends ChannelHandlerAdapter {
	
	private static final String MESSAGE = "Netty is a NIO client server framework which enables quick and easy development of network applications such as protocol servers and clients.";

	public ClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer(MESSAGE.getBytes()));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("接收服务器响应msg:[" + msg + "]");
	}
	

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		super.userEventTriggered(ctx, evt);
		IdleStateEvent event = (IdleStateEvent) evt;
		 // 如果IoSession闲置，则关闭连接
        if (event.state() == IdleState.READER_IDLE) {
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
            ctx.writeAndFlush(json);
        }
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}

package com.mws.web.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mws.web.net.bo.Constant;

public class MessageDecoder extends ByteToMessageDecoder {
	
	private static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 8) {
			return;
		}
		int magicWord = in.readInt();
		//码头不正确
		if(magicWord == Constant.magicWord){
			
			int msgLength = in.readInt();
			byte contents[] = new byte[msgLength];
			in.readBytes(contents);
//			logger.info("解码包数据:" + new String(contents,"utf-8"));
			out.add(new String(contents,"utf-8"));
		}else{
			logger.error("消息包码头不正确");
		}

	}

}

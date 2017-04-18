package com.mws.web.net.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.dto.MessageDto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class MessageEncoder extends MessageToByteEncoder<Object> {

    private static Logger logger = LoggerFactory.getLogger(MessageEncoder.class);


    Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        String message;
        if (msg instanceof MessageDto.Request || msg instanceof MessageDto.Response) {
            message = JsonMapper.nonDefaultMapper().toJson(msg);
        } else {
            message = msg.toString();
        }
        out.writeInt(Constant.magicWord);
        out.writeInt(message.getBytes(charset.name()).length);
        out.writeBytes(message.getBytes(charset.name()));
        String clientIP = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
//        logger.info("[" + clientIP + "]发送编码包数据:" + message);
    }

}

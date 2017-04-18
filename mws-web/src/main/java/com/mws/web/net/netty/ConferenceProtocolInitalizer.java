package com.mws.web.net.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ConferenceProtocolInitalizer extends ChannelInitializer<SocketChannel> {

    private ChannelHandler handler;
    private final int IDLE_TIME = 15;  //连接检测空闲时间
    private final int READ_TIME = 15; //读超时时间
    private final int WRITE_TIME = 15; //写超时时间

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addFirst(new LengthFieldBasedFrameDecoder(1024 * 1024, 4, 4, 0, 0));
        pipeline.addLast(new IdleStateHandler(IDLE_TIME, READ_TIME, WRITE_TIME));
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new ConferenceHandler());

    }

    public ChannelHandler getHandler() {
        return handler;
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }

}

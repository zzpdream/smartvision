package com.mws.web.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import com.mws.web.net.netty.ClientHandler;
import com.mws.web.net.netty.MessageDecoder;
import com.mws.web.net.netty.MessageEncoder;

public class NettyClientTest {

	/**
	 * 链接服务器
	 * 
	 * @param port
	 * @param host
	 * @throws Exception
	 */
	public void connect(int port, String host) throws Exception {
		// 网络事件处理线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// 配置客户端启动类
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)// 设置封包
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addFirst(new IdleStateHandler(5, 5, 5));
							ch.pipeline().addLast(new MessageDecoder());// 设置字符串解码器
							ch.pipeline().addLast(new MessageEncoder());// 设置字符串解码器
							ch.pipeline().addLast(new ClientHandler());// 设置客户端网络IO处理器
						}
					});
			// 连接服务器 同步等待成功
			ChannelFuture f = b.connect(host, port).sync();
			// 同步等待客户端通道关闭
			f.channel().closeFuture().sync();
		} finally {
			// 释放线程组资源
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 5678;
		new NettyClientTest().connect(port, "127.0.0.1");

	}
}

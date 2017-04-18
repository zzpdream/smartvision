package com.mws.web.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConferenceServer {

	private static Logger logger = LoggerFactory.getLogger(ConferenceServer.class);

	private volatile EventLoopGroup workerGroup;
	private volatile EventLoopGroup bossGroup;
	private volatile ServerBootstrap bootstrap;
	private ChannelFuture serverChannelFuture;

	/**
	 * 启动netty的控制线程
	 */
	private Executor messageExecutor;

	/**
	 * 下面几个是由spring注入
	 */
	private int port;
	private Map<ChannelOption<Object>, Object> channelOptions;
	private ChannelHandler channelHandler;

	/**
	 * 初始化netty启动配置
	 */
	public void init() {
		workerGroup = new NioEventLoopGroup();
		bossGroup = new NioEventLoopGroup();
		try {
			bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(channelHandler);
			for (Map.Entry<ChannelOption<Object>, Object> entry : channelOptions.entrySet()) {
				bootstrap.option(entry.getKey(), entry.getValue());
			}
			serverChannelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
			logger.info("成功bind端口:" + port);

			serverChannelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("启动NETTY TCP异常:", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	/**
	 * 开启单独的线程运行netty服务,避免和spring mvc冲突
	 */
	public void run(){
		messageExecutor = Executors.newFixedThreadPool(1);
		messageExecutor.execute(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}

	public void destroy() throws Exception {
		bossGroup.close();
		workerGroup.close();
		serverChannelFuture.channel().closeFuture().sync();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<ChannelOption<Object>, Object> getChannelOptions() {
		return channelOptions;
	}

	public void setChannelOptions(
			Map<ChannelOption<Object>, Object> channelOptions) {
		this.channelOptions = channelOptions;
	}

	public ChannelHandler getChannelHandler() {
		return channelHandler;
	}

	public void setChannelHandler(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}



}

package com.hx.nettyserver.server.boostrap;

import com.hx.nettycommon.annotation.config.NettyConfigProperties;
import com.hx.nettycommon.util.ConfigUtlis;
import com.hx.nettyserver.server.initializer.NettyServerInitializer;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

@Component
public class NettyServer {
    private static final Logger logger  = LogManager.getLogger(NettyServer.class);


    private ServerBootstrap b;

    @Autowired
    private NettyConfigProperties nettyServerConfig;

    @Autowired
    private NettyServerInitializer nettyServerInitializer;

    private String address;
    private int port;

    public NettyServer() {
    }

    public NettyServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start(){
        /**
         * 定义一对线程组（两个线程池）
         *
         */
        //主线程组，用于接收客户端的链接，但不做任何处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(nettyServerConfig.getServerBossThreadNum(),new DefaultThreadFactory("serverBoss",true));
        //定义从线程组，主线程组会把任务转给从线程组进行处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(nettyServerConfig.getServerWorkerThreadNum(),new DefaultThreadFactory("serverWork",true));

        //加载appId
        ConfigUtlis.setAppId(nettyServerConfig.getAppId());

        try {
            /**
             * 服务启动类，任务分配自动处理
             *
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            InetSocketAddress localAddress = new InetSocketAddress(nettyServerConfig.getLocalHostaddress(),nettyServerConfig.getLocalHostport());
            //需要去针对一个之前的线程模型（上面定义的是主从线程）
            serverBootstrap.group(bossGroup, workerGroup)
                //设置NIO的双向通道
                .channel(NioServerSocketChannel.class)
                .localAddress(localAddress)
                .option(ChannelOption.SO_BACKLOG, 1024)// 配置TCP参数
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //子处理器，用于处理workerGroup
                /**
                 * 设置chanel初始化器
                 * 每一个chanel由多个handler共同组成管道(pipeline)
                 */
                .childHandler(nettyServerInitializer);

            /**
             * 启动
             *
             */
            //绑定端口，并设置为同步方式，是一个异步的chanel
            ChannelFuture cf = serverBootstrap.bind(localAddress).sync();
            logger.info("Server start listen at " + localAddress.getPort());
            cf.channel().closeFuture().sync();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //使用一种优雅的方式进行关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}

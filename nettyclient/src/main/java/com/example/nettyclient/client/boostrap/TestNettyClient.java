package com.example.nettyclient.client.boostrap;

import com.example.nettyclient.client.initializer.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class TestNettyClient {

    private static final Logger logger  = LogManager.getLogger(TestNettyClient.class);

    private static void connect(Bootstrap bootstrap, String host, int port,int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (8 - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    public void start(InetSocketAddress remoteAddress,InetSocketAddress localAddress){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    // 1.指定线程模型
                    .group(workerGroup)
                    // 2.指定 IO 类型为 NIO
                    .channel(NioSocketChannel.class)
                    //3.要求低延迟，禁用Nagle算法，根据MSS分包发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    //4.设置需要远程访问的地址
                    .remoteAddress(remoteAddress)
                    .localAddress(localAddress)
                    // 5.IO 处理逻辑
                    .handler(new ClientChannelInitializer());

            //6.建立连接
            ChannelFuture future =bootstrap.connect(remoteAddress.getAddress().getHostAddress(), remoteAddress.getPort()).addListener(result -> {
                if (result.isSuccess()) {
                    System.out.println("连接成功!");
                } else {
                    System.err.println("连接失败!");
                    connect(bootstrap, remoteAddress.getAddress().getHostAddress(), remoteAddress.getPort(),4);
                }
            }).sync();
            //7.绑定端口，开始递送进来的连接，推送消息
            future.channel().writeAndFlush("Hello Netty-Server");
            logger.info("------ 已发送数据到"+remoteAddress.getAddress().getHostAddress()+":"+remoteAddress.getPort());
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();

        }
    }



}

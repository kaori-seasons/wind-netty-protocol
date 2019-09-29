package com.example.nettyclient.client.boostrap;

import com.example.nettyclient.client.common.conf.NettyClientConfig;
import com.example.nettyclient.client.initializer.ClientChannelInitializer;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


@Component
public class NettyClient {

    private static final Logger logger  = LogManager.getLogger(NettyClient.class);
    //主机
    private String host;
    //端口号
    private int port;
    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf;

    @Autowired
    private NettyClientConfig nettyClientConfig;

    public NettyClient(){

    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private ChannelFuture connect(Bootstrap bootstrap,String host, int port,int retry) {
        ChannelFuture result = bootstrap.connect(host, port).addListener(future -> {
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
                b.config().group().schedule(() -> connect(b, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });

        return result;
    }

    @PostConstruct
    public void start(){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        InetSocketAddress remoteAddress = new InetSocketAddress(nettyClientConfig.getRemoteHostAddress(),nettyClientConfig.getRemoteHostPort());
        InetSocketAddress localAddress = new InetSocketAddress(nettyClientConfig.getLocalHostaddress(),nettyClientConfig.getLocalHostport());

        try {
            b = new Bootstrap();
            b
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
            ChannelFuture future = connect(b,remoteAddress.getAddress().getHostAddress(),remoteAddress.getPort(),5).sync();
            future.channel().closeFuture().sync();
            workerGroup.shutdownGracefully();
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    public ChannelFuture getChannelFuture() {
        return this.cf;
    }

    //当前频道的未发起连接
    public void getOnlineChannel(Bootstrap b,String host, int port,int retry) {
        // 如果没有连接先链接
        if (this.cf == null) {
            this.connect(b,host, port,retry);
        } // this.cf.channel().isActive() 这里得到的是链接状态
        if (!this.cf.channel().isActive()) {
            this.connect(b,host, port,retry);
        }
    }

    //释放资源
    public void close() {
        try {
            cf.channel().closeFuture().sync();
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

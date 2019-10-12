package com.hx.nettyclient.client.boostrap;

import com.hx.nettyclient.client.initializer.ClientChannelInitializer;
import com.hx.nettycommon.annotation.config.NettyConfigProperties;
import com.hx.nettycommon.util.ConfigUtlis;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class NettyClient {

    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf;

    @Autowired
    private NettyConfigProperties nettyClientConfig;

    public NettyClient() {

    }

    public void connect() {
        if (cf != null || cf.channel().isActive()) {
            return;
        }
        cf = connect(b, nettyClientConfig.getRemoteHostAddress(), nettyClientConfig.getRemoteHostPort(), 0);
    }

    private ChannelFuture connect(Bootstrap bootstrap, String host, int port, int retry) {
        cf = bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("客户端连接成功!");
            } else if (retry == 0) {
                log.error("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (8 - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.error("连接失败，第{}次重连...", order);
                b.config().group().schedule(() -> connect(b, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
        return cf;
    }

    @PostConstruct
    public void start() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        InetSocketAddress remoteAddress = new InetSocketAddress(nettyClientConfig.getRemoteHostAddress(), nettyClientConfig.getRemoteHostPort());
        InetSocketAddress localAddress = new InetSocketAddress(nettyClientConfig.getLocalHostaddress(), nettyClientConfig.getLocalHostport());

        //存储appId
        ConfigUtlis.setAppId(nettyClientConfig.getAppId());

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
                    .handler(new ClientChannelInitializer(this));

            this.getChannelFuture();
        } catch (Exception e) {
            this.close();
        }
    }

    //当前频道的未发起连接
    public ChannelFuture getChannelFuture() {
        InetSocketAddress remoteAddress = new InetSocketAddress(nettyClientConfig.getRemoteHostAddress(), nettyClientConfig.getRemoteHostPort());
        // 如果没有连接先链接
        if (this.cf == null) {
            this.connect(b, remoteAddress.getAddress().getHostAddress(), remoteAddress.getPort(), 5);
        }
        return this.cf;
    }

    public ChannelFuture getFutrue() {
        return this.cf;
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


//    protected void reConnect() {
//        if (cf != null && cf.channel().isActive()) {
//            return;
//        }
//
//        ChannelFuture future = bootstrap.connect("127.0.0.1", 12345);
//
//        future.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture futureListener) throws Exception {
//                if (futureListener.isSuccess()) {
//                    channel = futureListener.channel();
//                    System.out.println("Connect to server successfully!");
//                } else {
//                    System.out.println("Failed to connect to server, try connect after 10s");
//
//                    futureListener.channel().eventLoop().schedule(new Runnable() {
//                        @Override
//                        public void run() {
//                            doConnect();
//                        }
//                    }, 10, TimeUnit.SECONDS);
//                }
//            }
//        });
//    }

}

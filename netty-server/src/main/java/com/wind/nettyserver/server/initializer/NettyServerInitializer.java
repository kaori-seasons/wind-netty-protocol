package com.hx.nettyserver.server.initializer;

import com.hx.nettyserver.server.handler.HeartbeatHandler;
import com.hx.nettyserver.server.handler.ServerIdleStateHandler;
import com.hx.nettyserver.server.handler.ServerReceivceAppInfoHandler;
import com.hx.nettyserver.server.handler.ServerReplyAppInfoHandler;
import com.hx.nettyserver.server.handler.ServerSendMsgHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerReceivceAppInfoHandler receivceAppInfoHandler;

    @Autowired
    private ServerSendMsgHandler serverSendMsgHandler;

    @Autowired
    private ServerReplyAppInfoHandler serverReplyAppInfoHandler;


    //对chanel进行初始化
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel去获得对应的管道
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("decoder",new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        //p.addLast("decoder",new JsonDecoder());
        //p.addLast("encoder", new JsonEncoder());
        p.addLast(new ServerIdleStateHandler());
        p.addLast(new HeartbeatHandler());
        p.addLast(receivceAppInfoHandler);
        p.addLast(serverReplyAppInfoHandler);
        p.addLast(serverSendMsgHandler);
    }

}

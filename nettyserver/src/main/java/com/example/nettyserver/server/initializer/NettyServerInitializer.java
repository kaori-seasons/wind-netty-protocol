package com.example.nettyserver.server.initializer;

import com.example.nettyserver.server.handler.ServerIdleStateHandler;
import com.example.nettyserver.server.handler.ServerReceivceMsgHandler;
import com.example.nettyserver.server.handler.ServerSendMsgHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

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
        p.addLast(new ServerReceivceMsgHandler());
        p.addLast(new ServerSendMsgHandler());
    }

}

package com.example.nettyclient.client.initializer;

import com.example.nettyclient.client.handler.ClientHandler;
import com.example.nettyclient.client.handler.HeartBeatTimerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p  = ch.pipeline();
        p.addLast("decoder",new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        //p.addLast("decoder",new JsonDecoder());
        //p.addLast("encoder", new JsonEncoder());
        p.addLast(new HeartBeatTimerHandler());
        p.addLast(new ClientHandler());
    }
}

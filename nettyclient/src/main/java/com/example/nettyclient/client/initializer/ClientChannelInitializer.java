package com.example.nettyclient.client.initializer;

import com.example.nettyclient.client.handler.ClientHandler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {


    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline p  = ch.pipeline();
        // 基于定长的方式解决粘包/拆包问题
        p.addLast(new LengthFieldPrepender(2));
        p.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        // 心跳机制
        p.addLast(new IdleStateHandler(3, 10, 0, TimeUnit.SECONDS));
        //解码器
        p.addLast("decoder",new ObjectDecoder(1024*1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        p.addLast(new ClientHandler());
    }
}

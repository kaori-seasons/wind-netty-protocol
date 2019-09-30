package com.example.nettyserver.server.initializer;

import com.example.nettyserver.server.handler.ServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


public class ServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    //对chanel进行初始化
    @Override
    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
        //通过socketChannel去获得对应的管道
        ChannelPipeline p = socketChannel.pipeline();
        /**
         * pipeline中会有很多handler类（也称之拦截器类）
         * 获得pipeline之后，可以直接.add，添加不管是自己开发的handler还是netty提供的handler
         */
        p.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        p.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
        p.addLast(new LengthFieldPrepender(2));
        p.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        p.addLast("decoder",new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        p.addLast(new ServerHandler());

    }

}

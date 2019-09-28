package com.example.nettyserver.server.initializer;

import com.example.nettyserver.server.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;


public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    //对chanel进行初始化
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel去获得对应的管道
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        /**
         * pipeline中会有很多handler类（也称之拦截器类）
         * 获得pipeline之后，可以直接.add，添加不管是自己开发的handler还是netty提供的handler
         */
        channelPipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        channelPipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
        channelPipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast(new ServerHandler());

    }

}

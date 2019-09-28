package com.example.nettyclient.client.initializer;

import com.example.nettyclient.client.handler.ClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p  = ch.pipeline();
        p.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        p.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        p.addLast(new ClientHandler());
    }
}

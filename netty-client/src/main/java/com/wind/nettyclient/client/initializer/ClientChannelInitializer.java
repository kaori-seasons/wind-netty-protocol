package com.hx.nettyclient.client.initializer;

import com.hx.nettyclient.client.boostrap.NettyClient;
import com.hx.nettyclient.client.handler.ClientMsgHandler;
import com.hx.nettyclient.client.handler.ClientSendAppInfoHandler;
import com.hx.nettyclient.client.handler.HeartBeatTimerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyClient nettyClient;

    public ClientChannelInitializer(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p  = ch.pipeline();
        p.addLast("decoder",new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        //p.addLast("decoder",new JsonDecoder());
        //p.addLast("encoder", new JsonEncoder());
        p.addLast(new IdleStateHandler(0, 0, 5));
        p.addLast(new HeartBeatTimerHandler(nettyClient));
        p.addLast(new ClientSendAppInfoHandler());
        p.addLast(new ClientMsgHandler());
    }
}

package com.example.nettyclient.client.handler;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String uid = "111";
        ctx.channel().writeAndFlush(uid);
        ctx.fireChannelRead(uid);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String repId = (String) msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + repId);
    }



}


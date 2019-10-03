/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author chengxy
 * 2019/10/1
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int READER_IDLE_TIME = 15;

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String uid = (String) msg;
        ctx.fireChannelRead(msg); //把数据传递给下一个handler
        scheduleSendHeartBeat(ctx,uid); //向服务端发送心跳

    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx,String uid) {
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                ctx.writeAndFlush("客户端"+uid+"向服务端发送心跳包");
                scheduleSendHeartBeat(ctx,uid);
            }

        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}

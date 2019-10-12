/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.handler;

import com.hx.nettyclient.client.boostrap.NettyClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengxy
 * 心跳处理器
 * 2019/10/9
 */
@Slf4j
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端实例
     */
    private NettyClient nettyClient;

    /**
     * 心跳次数统计
     */
    private int heartbeatCount;

    /**
     * 心跳请求
     */
    public static final byte PING = 1;

    public HeartBeatTimerHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof Byte) {
            log.debug("receive server heartbeat");
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    protected void sendPingMsg(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(PING);
        heartbeatCount++;
        log.debug("sent ping to {}, heartbeatCount:{}", ctx.channel().remoteAddress(), heartbeatCount);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.error("server:{} is active", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("server:{} is inactive", ctx.channel().remoteAddress());
        nettyClient.connect();
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        log.error("READER_IDLE");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        log.error("WRITER_IDLE");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        sendPingMsg(ctx);
    }

}

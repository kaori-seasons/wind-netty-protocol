package com.hx.nettyserver.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cj.luo
 * @date 2019/10/12
 */
@ChannelHandler.Sharable
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    /**
     * 心跳请求
     */
    private static final byte PING = 1;

    private int heartbeatCount;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Byte && msg.equals(PING)) {
            heartbeatCount++;
            log.debug("心跳请求 clientIp -> {} heartbeatCount -> {}", ctx.channel().remoteAddress(), heartbeatCount);
            ctx.channel().writeAndFlush(PING);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}

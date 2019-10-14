/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.handler;

import com.hx.nettyserver.server.manager.ChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chengxy
 * 2019/10/1
 */
@ChannelHandler.Sharable
@Slf4j
public class ServerIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 10;


    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            switch (evnet.state()) {
                case READER_IDLE:
                    // 判断Channel是否读空闲, 读空闲时移除Channel
                    ChannelManager.removeChannel(ctx.channel());
                    break;
                default:
                    break;
            }
        }
        ctx.fireUserEventTriggered(evt);
    }
}

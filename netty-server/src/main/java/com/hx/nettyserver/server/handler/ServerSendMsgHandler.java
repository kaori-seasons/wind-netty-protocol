/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.handler;

import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.listen.parent.Listener;
import com.hx.nettyserver.server.listen.push.PushService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chengxy
 * 2019/10/9
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerSendMsgHandler extends ChannelInboundHandlerAdapter {


    private PushService<BaseAppMetaDataBO> pushService = new PushService<>();

    @Resource(name = "notifyTransferDTO")
    private Listener listeners;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof BaseAppMetaDataBO) {
            BaseAppMetaDataBO baseAppMetaDataBO = (BaseAppMetaDataBO) msg;
            log.debug("server received：{}", baseAppMetaDataBO.getEntcryStr());
            pushService.registerListener(listeners);
            pushService.sendMessage(baseAppMetaDataBO);
            ctx.channel().writeAndFlush("server received...");
            return;
        }
        ctx.channel().writeAndFlush("handler chan end!");
    }
}

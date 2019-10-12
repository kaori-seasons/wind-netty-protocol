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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author chengxy
 * 2019/10/9
 */
@Component
@ChannelHandler.Sharable
public class ServerSendMsgHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(ServerSendMsgHandler.class);


    private PushService<BaseAppMetaDataBO> pushService = new PushService<>();

    @Resource(name = "notifyTransferDTO")
    private Listener listeners;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        if (msg instanceof BaseAppMetaDataBO) {
            BaseAppMetaDataBO baseAppMetaDataBO = (BaseAppMetaDataBO) msg;
            logger.debug("服务端接收到消息： "+ baseAppMetaDataBO.getEntcryStr());
            pushService.registerListener(listeners);
            pushService.sendMessage(baseAppMetaDataBO);
        }


    }
}

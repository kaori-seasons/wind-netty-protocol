/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.handler;

import com.hx.nettyclient.client.listen.push.PushService;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.listen.parent.Listener;

import javax.annotation.Resource;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengxy
 * 业务消息处理器
 * 2019/10/9
 */
@Slf4j
public class ClientMsgHandler extends ChannelInboundHandlerAdapter {

    private PushService<BaseAppMetaDataBO> pushTransferInfo = new PushService<>();

    @Resource(name = "notifyTransferDTO")
    private Listener listeners;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof BaseAppMetaDataBO) {
            BaseAppMetaDataBO baseAppMetaDataBO = (BaseAppMetaDataBO) msg;
            pushTransferInfo.registerListener(listeners);
            pushTransferInfo.sendMessage(baseAppMetaDataBO);
            pushTransferInfo.notifyListener();
        }

    }

}

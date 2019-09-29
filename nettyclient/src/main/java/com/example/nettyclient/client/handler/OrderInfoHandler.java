/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.handler;

import com.example.nettyclient.client.entity.OrderInfoResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author chengxy
 * 2019/9/29
 */
public class OrderInfoHandler extends SimpleChannelInboundHandler<OrderInfoResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OrderInfoResponsePacket msg) throws Exception {
        Long fromTraceNo = msg.getFromTradeNo();
        String fromOrderMessage = msg.getFromOrderMessage();
    }
}

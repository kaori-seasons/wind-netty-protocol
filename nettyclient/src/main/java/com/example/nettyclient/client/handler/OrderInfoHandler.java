/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.handler;

import com.example.nettyclient.client.common.packet.PackCodeC;
import com.example.nettyclient.client.common.packet.Packet;
import com.example.nettyclient.client.entity.OrderInfoResponsePacket;

import java.nio.charset.Charset;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author chengxy
 * 2019/9/29
 */
public class OrderInfoHandler extends SimpleChannelInboundHandler<OrderInfoResponsePacket> {


    private static final Logger logger  = LogManager.getLogger(OrderInfoHandler.class);

    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data;
    private long readByte;
    private long contentLength;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OrderInfoResponsePacket msg) throws Exception {
        Long fromTraceNo = msg.getFromTradeNo();
        String fromOrderMessage = msg.getFromOrderMessage();
    }

    public String getData() {
        return data;
    }

    //获取异步返回的结果
    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PackCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof OrderInfoResponsePacket){
            //如果是这个数据包
            OrderInfoResponsePacket orderInfoResponsePacket = (OrderInfoResponsePacket) packet;
            if (orderInfoResponsePacket.isSuccess()) {
                logger.info(new Date() + "订单发送成功");
            }else {
                logger.info(new Date()+"订单发送失败");
            }
        }

        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            contentLength = Long.parseLong(response.headers().get(HttpHeaders.Names.CONTENT_LENGTH));
            readByte = 0;
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            readByte += buf.readableBytes();
            data += buf.toString(Charset.forName("gb2312"));
            if (readByte >= contentLength) {
                promise.setSuccess();
            }
            buf.release();
        }
    }
}

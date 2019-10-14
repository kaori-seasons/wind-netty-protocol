/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.handler;

import com.hx.nettycommon.entity.ResponseResult;
import com.hx.nettyserver.server.manager.ChannelManager;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengxy
 * 2019/10/2
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerReplyAppInfoHandler extends ChannelInboundHandlerAdapter {

    //把响应写给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ResponseResult) {
            ResponseResult responseResult = (ResponseResult) msg;
            log.debug("服务端接收客户端appId={}，响应类型:{}", responseResult.getAppId(), responseResult.getResultCode());
            responseResult.setResultCode("1");
            ChannelManager.addChannel(ctx.channel(), responseResult.getAppId());
            ctx.channel().writeAndFlush(responseResult.getResultCode());
            log.info("服务端返回:{} ", responseResult.toString());
        } else {
            ctx.fireChannelRead(msg);
        }
    }


    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        log.info("服务端返回消息超时:{}", clientIp);


    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        log.error("服务端推送消息异常: {}", cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author chengxy
 * 2019/10/2
 */
@Component
@ChannelHandler.Sharable
public class ServerReplyAppInfoHandler extends ChannelInboundHandlerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(ServerReplyAppInfoHandler.class);

//    @Autowired
//    private RequestDispatcher dispatcher; //后续将观察者模式模式改为基于spring分发器的远程调用


    //把响应写给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ResponseResult) {
            ResponseResult responseResult = (ResponseResult) msg;
            logger.debug("服务端读取到 " + "appid= " + responseResult.getAppId() + "响应类型为" + responseResult.getResultCode());
            responseResult.setResultCode("1");
            ChannelManager.addChannel(ctx.channel(), responseResult.getAppId());
            ctx.channel().writeAndFlush(responseResult.getResultCode());
            logger.debug("服务端写出: " + responseResult.toString());

            // 转换为MethodInvokeMeta 后期拓展使用
            //MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
            //logger.info("{} -> [客户端信息] \n 方法名  - > {} \n 参数列表  -> {} \n " +
            //        "返回值  ->  {} ", this.getClass().getName(), invokeMeta.getMethodName(), invokeMeta.getArgs()
            //    , invokeMeta.getReturnType());

            //// 具体的处理类
            //this.dispatcher.dispatcher(ctx, invokeMeta);
        }else {
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
        logger.debug("服务端写入消息超时 : " + clientIp);


    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        logger.error("服务端推送消息异常: {}",cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}

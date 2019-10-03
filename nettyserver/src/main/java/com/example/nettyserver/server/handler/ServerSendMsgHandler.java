/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.handler;

import com.example.demo.entity.ResponseResult;
import com.example.demo.remote.MethodInvokeMeta;
import com.example.demo.remote.dispatcher.RequestDispatcher;
import com.example.nettyserver.server.manager.ChannelManager;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author chengxy
 * 2019/10/2
 */
@ChannelHandler.Sharable
public class ServerSendMsgHandler extends ChannelInboundHandlerAdapter {


    private static final Logger logger  = LogManager.getLogger(ServerSendMsgHandler.class);

    @Autowired
    private RequestDispatcher dispatcher;

    //把响应写给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseResult responseResult  = (ResponseResult) msg;
        ChannelManager.broadcastMess(responseResult.getUid(),responseResult.getResultCode());

        // 转换为MethodInvokeMeta
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        logger.info("{} -> [客户端信息] \n 方法名  - > {} \n 参数列表  -> {} \n " +
                "返回值  ->  {} ", this.getClass().getName(), invokeMeta.getMethodName(), invokeMeta.getArgs()
            , invokeMeta.getReturnType());


        //// 具体的处理类
        //this.dispatcher.dispatcher(ctx, invokeMeta);
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
        System.out.println("channelInactive:" + clientIp);


    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}

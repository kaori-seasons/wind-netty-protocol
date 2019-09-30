package com.example.nettyclient.client.handler;

import com.example.nettyclient.client.common.spring.NettyRequestDispatcher;
import com.example.nettyclient.client.common.metadata.MethodInvokeMeta;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger  = LogManager.getLogger(ClientHandler.class);

    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data;
    private long readByte;
    private long contentLength;

    //注入请求分发器
    @Autowired
    private NettyRequestDispatcher dispatcher;
    private int lossConnectCount = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            super.channelActive(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ctx = ctx;
        logger.info("ClientHandler Active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info(ctx.channel().remoteAddress()+"----->Server :"+msg.toString());
        logger.info("--------");
        logger.info("ClientHandler read Message:"+msg);


        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        logger.info("{} -> [客户端信息] \n 方法名  - > {} \n 参数列表  -> {} \n " +
                "返回值  ->  {} ", this.getClass().getName(), invokeMeta.getMethodName(), invokeMeta.getArgs()
            , invokeMeta.getReturnType());
        this.dispatcher.dispatcher(ctx, invokeMeta);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

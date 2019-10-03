package com.example.nettyserver.server.handler;

import com.example.demo.entity.ResponseResult;
import com.example.nettyserver.server.listen.PushService;
import com.example.nettyserver.server.manager.ChannelManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//处理的请求是：客户端向服务端发起送数据，先把数据放在缓冲区，服务器端再从缓冲区读取，类似于[ 入栈, 入境 ]
public class ServerReceivceMsgHandler extends ChannelInboundHandlerAdapter {


    private static final Logger logger  = LogManager.getLogger(ServerReceivceMsgHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String uid = (String) msg;
        logger.info(new Date() + ": 服务端读到数据 -> " + uid);
        if (uid.equals("111")) {
            String requestSuccess = "1";
            ResponseResult responseResult  = new ResponseResult();
            responseResult.setUid(uid);
            ChannelManager.addChannel(ctx.channel(),uid);
            responseResult.setResultCode(requestSuccess);

            PushService pushService = new PushService();
            pushService.sendMessage("1111");


            //返回一个响应码给client
            ctx.fireChannelRead(responseResult);
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
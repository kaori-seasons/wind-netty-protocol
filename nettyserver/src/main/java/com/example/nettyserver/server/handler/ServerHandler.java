package com.example.nettyserver.server.handler;

import com.example.nettyserver.server.entity.OrgInfo;
import com.example.nettyserver.server.manager.NettyServerManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

//处理的请求是：客户端向服务端发起送数据，先把数据放在缓冲区，服务器端再从缓冲区读取，类似于[ 入栈, 入境 ]
public class ServerHandler extends ChannelInboundHandlerAdapter {//Http请求，所以使用HttpObject
    private static final Logger logger  = LogManager.getLogger(ServerHandler.class);

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive----->");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //logger.info("server channelRead......"+msg.toString());

        OrgInfo orgInfo = (OrgInfo) msg;
        System.out.println("服务端收到消息来自 : " + orgInfo.getAddr());
        ctx.channel().writeAndFlush(orgInfo);
        logger.info(" --------- 已发送数据 ---------- 到"+ctx.channel().remoteAddress());
    }

    /*
     * 握手建立
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
    }


    /*
     * 握手取消
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.remove(incoming);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (evnet.state().equals(IdleState.READER_IDLE)) {
                NettyServerManager.removeChannel(ctx.channel());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
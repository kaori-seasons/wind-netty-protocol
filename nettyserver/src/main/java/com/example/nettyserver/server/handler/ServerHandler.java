package com.example.nettyserver.server.handler;

import com.example.nettyserver.server.common.packet.PackCodeC;
import com.example.nettyserver.server.common.packet.Packet;
import com.example.nettyserver.server.entity.OrderInfoRequestPacket;
import com.example.nettyserver.server.entity.OrderInfoResponsePacket;
import com.example.nettyserver.server.manager.NettyServerManager;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
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
        System.out.println("server channelRead......");
        System.out.println(ctx.channel().remoteAddress()+"----->Server :"+ msg.toString());
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PackCodeC.INSTANCE.decode(requestByteBuf);
        if (packet instanceof OrderInfoRequestPacket){
            logger.info(new Date()+": 收到客户端请求");
            OrderInfoRequestPacket orderInfoRequestPacket = (OrderInfoRequestPacket) packet;
            OrderInfoResponsePacket orderInfoResponsePacket = new OrderInfoResponsePacket();
            orderInfoResponsePacket.setVersion(packet.getVersion());
            orderInfoResponsePacket.setFromOrderMessage("订单已收到");

            ByteBuf byteBuf = ctx.alloc().ioBuffer();
            ByteBuf reponseByteBuf = PackCodeC.INSTANCE.encode(byteBuf,orderInfoResponsePacket);
            ctx.channel().writeAndFlush(reponseByteBuf);

        }

        //定义发送的消息（不是直接发送，而是要把数据拷贝到缓冲区，通过缓冲区）
        //Unpooed：是一个专门用于拷贝Buffer的深拷贝，可以有一个或多个
        //CharsetUtil.UTF_8：Netty提供
        //ByteBuf content = Unpooled.copiedBuffer("Hello Netty", CharsetUtil.UTF_8);
        //ctx.channel().writeAndFlush(content);
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
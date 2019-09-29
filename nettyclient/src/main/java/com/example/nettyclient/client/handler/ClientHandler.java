package com.example.nettyclient.client.handler;

import com.example.nettyclient.client.common.packet.PackCodeC;
import com.example.nettyclient.client.common.packet.Packet;
import com.example.nettyclient.client.entity.OrderInfoResponsePacket;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
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

    //获取异步返回的结果
    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

    public String getData() {
        return data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info(ctx.channel().remoteAddress()+"----->Server :"+msg.toString());
        logger.info("--------");
        logger.info("ClientHandler read Message:"+msg);

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



        //if (msg instanceof HttpResponse) {
        //    HttpResponse response = (HttpResponse) msg;
        //    contentLength = Long.parseLong(response.headers().get(HttpHeaders.Names.CONTENT_LENGTH));
        //    readByte = 0;
        //}
        //if (msg instanceof HttpContent) {
        //    HttpContent content = (HttpContent) msg;
        //    ByteBuf buf = content.content();
        //    readByte += buf.readableBytes();
        //    data += buf.toString(Charset.forName("gb2312"));
        //    if (readByte >= contentLength) {
        //        promise.setSuccess();
        //    }
        //    buf.release();
        //}
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

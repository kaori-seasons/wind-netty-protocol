package com.hx.nettyclient.client.handler;

import com.hx.nettycommon.util.ConfigUtlis;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientSendAppInfoHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String uid = ConfigUtlis.getAppId();
        ctx.channel().writeAndFlush(uid);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            String reponse = (String) msg;
            log.debug("server response :{} ", reponse);
        } else {
            ctx.fireChannelRead(msg);
        }


    }


}


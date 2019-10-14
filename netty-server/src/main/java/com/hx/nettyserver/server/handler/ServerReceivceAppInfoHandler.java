package com.hx.nettyserver.server.handler;

import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.entity.ResponseResult;
import com.hx.nettycommon.listen.parent.Listener;
import com.hx.nettyserver.server.listen.push.PushService;
import com.hx.nettyserver.server.manager.ChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;

//处理的请求是：客户端向服务端发起送数据，先把数据放在缓冲区，服务器端再从缓冲区读取，类似于[ 入栈, 入境 ]
@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerReceivceAppInfoHandler extends ChannelInboundHandlerAdapter {

    private PushService<String> pushAppInfo = new PushService<>();

    @Resource(name = "notifyAppId")
    private Listener listeners;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof String) {
            String appId = (String) msg;
            log.debug("服务端接收appId -> {} ", appId);

            pushAppInfo.registerListener(listeners);
            pushAppInfo.sendMessage(appId); //推送appId到支付中心做校验
            String requestSuccess = "1";
            ResponseResult responseResult = new ResponseResult();
            responseResult.setAppId(appId);
            ChannelManager.addChannel(ctx.channel(), appId);
            responseResult.setResultCode(requestSuccess);
            ctx.fireChannelRead(responseResult);
        } else if (msg instanceof BaseAppMetaDataBO) {
            BaseAppMetaDataBO appMetaDataBO = (BaseAppMetaDataBO) msg;
            ctx.fireChannelRead(appMetaDataBO);
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
        log.info("服务端读取消息超时 :{}", clientIp);
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        log.error("服务端推送消息异常 :{} ", cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }


}
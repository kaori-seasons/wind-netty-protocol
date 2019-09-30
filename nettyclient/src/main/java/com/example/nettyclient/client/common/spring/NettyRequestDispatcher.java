/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.spring;

import com.example.nettyclient.client.common.conf.NettyConstant;
import com.example.nettyclient.client.common.metadata.MethodInvokeMeta;
import com.example.nettyclient.client.common.metadata.NullWritable;
import com.example.nettyclient.client.common.util.ResponseCodeEnum;
import com.example.nettyclient.client.common.util.ResponseResult;
import com.example.nettyclient.client.common.util.ResponseResultUtil;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chengxy
 * 2019/9/29
 */
@Component
public class NettyRequestDispatcher implements ApplicationContextAware {

    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());
    private ApplicationContext app;

    /**
     * 发送
     *
     * @param ctx
     * @param invokeMeta
     */
    public void dispatcher(final ChannelHandlerContext ctx, final MethodInvokeMeta invokeMeta) {
        executorService.submit(() -> {
            ChannelFuture f = null;
            try {
                Class<?> interfaceClass = invokeMeta.getInterfaceClass();
                String name = invokeMeta.getMethodName();
                Object[] args = invokeMeta.getArgs();
                Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
                Object targetObject = app.getBean(interfaceClass);
                Method method = targetObject.getClass().getMethod(name, parameterTypes);
                Object obj = method.invoke(targetObject, args);
                if (obj == null) {
                    f = ctx.writeAndFlush(NullWritable.nullWritable());
                } else {
                    f = ctx.writeAndFlush(obj);
                }
                f.addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e) {
                ResponseResult error = ResponseResultUtil.error(ResponseCodeEnum.SERVER_ERROR);
                f = ctx.writeAndFlush(error);
            } finally {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        });
    }

    /**
     * 加载当前application.xml
     *
     * @param ctx
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.app = ctx;
    }
}

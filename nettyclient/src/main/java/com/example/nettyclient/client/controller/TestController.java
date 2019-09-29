/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.controller;

import com.example.nettyclient.client.boostrap.NettyClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author chengxy
 * 2019/9/29
 */
@Controller
@RequestMapping("/order")
public class TestController {

    @Autowired
    private NettyClient nettyClient;

    @RequestMapping(value ="/test/{msg}",method = { RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String sendMsg(@PathVariable("msg") String msg) {
        //获取建立的channel
        ChannelFuture cf = nettyClient.getChannelFuture();
        Channel channel = cf.channel();
        System.out.println("testcontroller获得的channel---" + channel.toString());
        byte[] req = msg.getBytes();
        ByteBuf sendMsg= Unpooled.buffer(req.length);
        sendMsg.writeBytes(req);
        cf.channel().writeAndFlush(sendMsg);
        return "1";
    }

}

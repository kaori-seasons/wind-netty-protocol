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
import org.springframework.web.bind.annotation.RequestMapping;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author chengxy
 * 2019/9/30
 */
@Controller
@RequestMapping("/order")
public class ClientController {

    @Autowired
    private NettyClient nettyClient;

    @RequestMapping("/tranto")
    public String transToMessage(){
        ChannelFuture channelFuture = nettyClient.getChannelFuture();
        Channel channel = channelFuture.channel();
        String uuid = "111";
        return "1";
    }

}

/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.controller;

import com.hx.nettyclient.client.boostrap.NettyClient;
import com.hx.nettyclient.client.manager.TransferManager;
import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;

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
        ChannelFuture channelFuture = nettyClient.getFutrue();
        Channel channel = channelFuture.channel();
        BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO(); //新建需要传输的实体，这里传输的为顶级父类
        TransferManager.broadcastMess(baseAppMetaDataDTO,channel);
        return "1";
    }

}

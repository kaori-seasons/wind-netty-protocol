/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.entity;

import com.example.nettyserver.server.common.packet.Command;
import com.example.nettyserver.server.common.packet.Packet;

/**
 * @author chengxy
 * 2019/9/29
 */
public class OrderInfoRequestPacket extends Packet {


    private Long tradeNo;

    private String orderMessage;

    @Override
    public Byte getCommand() {
        return Command.ORDER_MESSAGE_REQUEST;
    }

    public Long getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(Long tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }
}

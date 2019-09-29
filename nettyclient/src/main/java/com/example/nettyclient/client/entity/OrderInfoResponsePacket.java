/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.entity;

import com.example.nettyclient.client.common.packet.Command;
import com.example.nettyclient.client.common.packet.Packet;

/**
 * @author chengxy
 * 2019/9/29
 */
public class OrderInfoResponsePacket extends Packet {

    private Long fromTradeNo;

    private String fromOrderMessage;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.ORDER_MESSAGE_RESPONSE;
    }

    public Long getFromTradeNo() {
        return fromTradeNo;
    }

    public void setFromTradeNo(Long fromTradeNo) {
        this.fromTradeNo = fromTradeNo;
    }

    public String getFromOrderMessage() {
        return fromOrderMessage;
    }

    public void setFromOrderMessage(String fromOrderMessage) {
        this.fromOrderMessage = fromOrderMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

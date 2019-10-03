/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.protocol.json.base;

import com.example.demo.Serializer.Serializer;
import com.example.demo.packet.Packet;

import io.netty.buffer.ByteBuf;

/**
 * @author chengxy
 * 2019/10/2
 */
public class JsonCodec  {

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        return null;
    }
}

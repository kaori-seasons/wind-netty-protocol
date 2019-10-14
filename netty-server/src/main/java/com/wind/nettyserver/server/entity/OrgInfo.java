package com.hx.nettyserver.server.entity;

import java.io.Serializable;

import io.netty.channel.Channel;

public class OrgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;  // UID
    private String addr;    // 地址
    private Channel channel;// 通道

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

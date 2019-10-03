package com.example.nettyserver.server.manager;

import com.example.demo.ChannelInfo;
import com.example.demo.util.NettyUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

/**
 * @author chengxy
 * 通道建立管理器
 * 2019/10/2
 */
public class ChannelManager {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<Channel, ChannelInfo> userInfos = new ConcurrentHashMap<Channel, ChannelInfo>();//保存连接信息，用于选择连接

    private static ConcurrentHashMap<String, ConcurrentMap<Channel, ChannelInfo>> channelInfos = new ConcurrentHashMap<>(); //保存连接uuid,用于标记连接

    /**
     * 登录注册 channel
     */
    public static synchronized void addChannel(Channel channel, String uid) {
        String remoteAddr = NettyUtils.getRemoteAddress(channel);
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setUserId(uid);
        channelInfo.setAddr(remoteAddr);
        channelInfo.setChannel(channel);
        userInfos.put(channel, channelInfo);
        channelInfos.put(uid, userInfos);
    }

    public static void removeChannel(Channel channel) {
        userInfos.remove(channel);
    }

    /**
     * 普通消息
     *
     * @param uid
     */
    public static void broadcastMess(String uid, Object data) {
        if (!StringUtil.isNullOrEmpty(uid)) {
            try {
                rwLock.readLock().lock();
                ConcurrentMap<Channel, ChannelInfo> currentChannel = channelInfos.get(uid);
                for (Channel ch : currentChannel.keySet()) {
                    //选择连接
                    ChannelInfo channelInfo = userInfos.get(ch);
                    if (!channelInfo.getUserId().equals(uid))
                        continue;
                    ch.writeAndFlush(data);
                    /*  responseToClient(ch,message);*/
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    public static synchronized ChannelInfo getUserInfo(String uid) {
        ConcurrentMap<Channel, ChannelInfo> currentChannel = channelInfos.get(uid);
        ChannelInfo channelInfo = new ChannelInfo();
        for (Channel ch : currentChannel.keySet()) {
            channelInfo = userInfos.get(ch);
            if (channelInfo != null) {
                return channelInfo;
            }
        }
        return channelInfo;
    }

    public static void main(String[] args) {
        // 发送 reqId
        //ChannelManager.getUserInfo("a").send();
        
    }
}

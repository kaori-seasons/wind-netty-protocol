package com.example.nettyserver.server.manager;

import com.example.nettyserver.server.entity.OrgInfo;
import com.example.nettyserver.server.util.NettyUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class NettyServerManager {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<Channel, OrgInfo> userInfos = new ConcurrentHashMap<Channel,OrgInfo>();
    /**
     * 登录注册 channel
     *
     *
     */
    public static void addChannel(Channel channel,String uid) {
        String remoteAddr = NettyUtils.getRemoteAddress(channel);
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setUserId(uid);
        orgInfo.setAddr(remoteAddr);
        orgInfo.setChannel(channel);
        userInfos.put(channel, orgInfo);
    }

    public static void removeChannel(Channel channel) {
        userInfos.remove(channel);
    }

    /**
     * 普通消息
     *
     * @param message
     */
    public static void broadcastMess(String uid,String message,String sender) {
        if (!StringUtil.isNullOrEmpty(message)) {
            try {
                rwLock.readLock().lock();
                Set<Channel> keySet = userInfos.keySet();
                for (Channel ch : keySet) {
                    OrgInfo userInfo = userInfos.get(ch);
                    if (!userInfo.getUserId().equals(uid) ) continue;
                    String backmessage=sender+","+message;
                    ch.writeAndFlush(new TextWebSocketFrame(backmessage));
                    /*  responseToClient(ch,message);*/
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    public static OrgInfo getUserInfo(Channel ch){
        OrgInfo user = userInfos.get(ch);
        return user;
    }
}

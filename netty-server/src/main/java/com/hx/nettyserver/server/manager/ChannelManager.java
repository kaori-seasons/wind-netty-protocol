package com.hx.nettyserver.server.manager;

import com.alibaba.fastjson.JSON;
import com.hx.nettycommon.ChannelInfo;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;
import com.hx.nettycommon.util.NettyUtils;
import com.hx.nettycommon.util.SymmetricCryptoUtils;
import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chengxy
 * 通道建立管理器
 * 2019/10/2
 */
public class ChannelManager {


    private final static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<Channel, ChannelInfo> userInfos = new ConcurrentHashMap<Channel, ChannelInfo>();//保存连接信息，用于选择连接

    private static ConcurrentHashMap<String, ConcurrentMap<Channel, ChannelInfo>> channelInfos = new ConcurrentHashMap<>(); //保存连接uappId,用于标记连接

    /**
     * 登录注册 channel
     */
    public static synchronized void addChannel(Channel channel, String appId) {
        String remoteAddr = NettyUtils.getRemoteAddress(channel);
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setUserId(appId);
        channelInfo.setAddr(remoteAddr);
        channelInfo.setChannel(channel);
        userInfos.put(channel, channelInfo);
        channelInfos.put(appId, userInfos);
        logger.info("服务端添加通道");
    }

    public static void removeChannel(Channel channel) {
        userInfos.remove(channel);
    }



    /**
     * 普通消息
     *
     * @param appId
     */
    public static void broadcastMess(String appId, BaseAppMetaDataDTO data) {
        if (!StringUtil.isNullOrEmpty(appId)) {
            try {
                rwLock.readLock().lock();
                ConcurrentMap<Channel, ChannelInfo> currentChannel = channelInfos.get(appId);
                for (Channel ch : currentChannel.keySet()) {
                    //选择连接
                    ChannelInfo channelInfo = userInfos.get(ch);
                    if (!channelInfo.getUserId().equals(appId))
                        continue;
                    String reqId = UUID.randomUUID().toString().replace("-", "");
                    data.setRequestId(reqId);
                    String result = JSON.toJSONString(data);
                    String encryptHex = SymmetricCryptoUtils.getInstance().encryptHex(result);
                    BaseAppMetaDataBO baseAppMetaDataBO = new BaseAppMetaDataBO();
                    baseAppMetaDataBO.setAppId(appId);
                    baseAppMetaDataBO.setEntcryStr(encryptHex);
                    baseAppMetaDataBO.setFlag(false);
                    ch.writeAndFlush(baseAppMetaDataBO);
                    logger.info("服务端发送消息");
                    /*  responseToClient(ch,message);*/
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    public static synchronized void broadcastMess(String appId, BaseAppMetaDataDTO data,String encryptStr){
        if (!StringUtil.isNullOrEmpty(appId)) {
            try {
                rwLock.readLock().lock();
                ConcurrentMap<Channel, ChannelInfo> currentChannel = channelInfos.get(appId);
                for (Channel ch : currentChannel.keySet()) {
                    //选择连接
                    ChannelInfo channelInfo = userInfos.get(ch);
                    if (!channelInfo.getUserId().equals(appId))
                        continue;
                    String reqId = UUID.randomUUID().toString().replace("-", "");
                    data.setRequestId(reqId);
                    String result = JSON.toJSONString(data);
                    String encryptHex  = SymmetricCryptoUtils.getInstance(encryptStr).encryptHex(result);
                    BaseAppMetaDataBO baseAppMetaDataBO = new BaseAppMetaDataBO();
                    baseAppMetaDataBO.setAppId(appId);
                    baseAppMetaDataBO.setEntcryStr(encryptHex);
                    baseAppMetaDataBO.setFlag(true);
                    ch.writeAndFlush(baseAppMetaDataBO);
                    logger.info("服务端发送消息");
                    /*  responseToClient(ch,message);*/
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    public static synchronized ChannelInfo getUserInfo(String appId) {
        ConcurrentMap<Channel, ChannelInfo> currentChannel = channelInfos.get(appId);
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

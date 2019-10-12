/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.manager;

import com.alibaba.fastjson.JSON;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;
import com.hx.nettycommon.util.ConfigUtlis;
import com.hx.nettycommon.util.SymmetricCryptoUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chengxy
 * 2019/10/7
 */
@Slf4j
public class TransferManager {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static BlockingQueue<Object> msgQueue = new ArrayBlockingQueue<Object>(4);

    private static UUID randomUUID = UUID.randomUUID();

    private static ConcurrentHashMap<String, Channel> chanelInfos = new ConcurrentHashMap<>(); //uid - > channelId 本次用的是哪一个channel进行传输
    private static ConcurrentHashMap<String, BlockingQueue<Object>> userInfos = new ConcurrentHashMap<>(); // repId -> entity 本次传输的对象

    /**
     * 添加本次传输的通道
     *
     * @param repId          本次传输的uid
     * @param currentChannel 本次选用的传输通道
     */
    public static void addChannel(String repId, Channel currentChannel) {
        chanelInfos.putIfAbsent(repId, currentChannel);
    }


    public static void addQueue(Object transfer) {
        msgQueue.add(transfer);
    }

    /**
     * 添加本次传输的实体
     *
     * @param repId 本次传输的repId
     */
    public static void addTransferObject(String repId) {
        userInfos.putIfAbsent(repId, msgQueue);
    }


    /**
     * 发送消息 需要自己加密
     *
     * @param appInfo
     * @param channel 选择通道
     */
    public static synchronized void broadcastMess(BaseAppMetaDataDTO appInfo, Channel channel) {
        broadcastMess(appInfo, channel, null);
    }

    public static synchronized void broadcastMess(BaseAppMetaDataDTO appInfo, Channel channel, String encrypt) {
        if (appInfo != null) {
            try {
                rwLock.readLock().lock();
                BaseAppMetaDataBO appMetaDataBO = new BaseAppMetaDataBO();
                appMetaDataBO.setAppId(ConfigUtlis.getAppId());
                appInfo.setRequestId(randomUUID.toString().replace("-", ""));
                String jsonStr = JSON.toJSONString(appInfo);
                String encryptStr = SymmetricCryptoUtils.getInstance(encrypt).encryptHex(jsonStr);
                appMetaDataBO.setEntcryStr(encryptStr);
                appMetaDataBO.setFlag(true);
                channel.writeAndFlush(appMetaDataBO);
                log.info("broadcass message body -> {}", jsonStr);
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

}

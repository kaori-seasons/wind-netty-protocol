/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.manager;

import com.alibaba.fastjson.JSON;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.hx.nettycommon.util.ConfigUtlis;
import com.hx.nettycommon.util.SymmetricCryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * @author chengxy
 * 服务端传输管理器
 * 2019/10/9
 */
@Slf4j
public class TransferManager {


    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static UUID randomUUID = UUID.randomUUID();


    private static BlockingQueue<BaseAppMetaDataDTO> reqMsgQueue = new ArrayBlockingQueue<BaseAppMetaDataDTO>(4); //发送消息使用的阻塞队列

    private static ConcurrentHashMap<String, BlockingQueue<BaseAppMetaDataDTO>> requestInfos = new ConcurrentHashMap<>(); //发送缓冲区


    /**
     * 发送消息 添加本次传输的实体
     *
     * @param baseReqMsg 本次传输的实体
     */
    public static void addReqQueue(BaseAppMetaDataDTO baseReqMsg) {
        String reqId = UUID.randomUUID().toString().replace("-", "");
        baseReqMsg.setRequestId(reqId);
        reqMsgQueue.add(baseReqMsg);
        requestInfos.putIfAbsent(baseReqMsg.getRequestId(), reqMsgQueue);
    }


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
                appMetaDataBO.setFlag(null != encrypt);
                channel.writeAndFlush(appMetaDataBO);
                log.info("broadcass message body -> {}", jsonStr);
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }


}

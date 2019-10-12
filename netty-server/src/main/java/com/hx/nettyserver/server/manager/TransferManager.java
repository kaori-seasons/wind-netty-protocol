/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.manager;

import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * @author chengxy
 * 服务端传输管理器
 * 2019/10/9
 */
public class TransferManager {


    private static Logger logger = LoggerFactory.getLogger(TransferManager.class);


    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);


    private static BlockingQueue<BaseAppMetaDataDTO> reqMsgQueue = new ArrayBlockingQueue<BaseAppMetaDataDTO>(4); //发送消息使用的阻塞队列

    private static ConcurrentHashMap<String,BlockingQueue<BaseAppMetaDataDTO>> requestInfos = new ConcurrentHashMap<>(); //发送缓冲区


    /**
     * 发送消息 添加本次传输的实体
     *
     * @param baseReqMsg 本次传输的实体
     */
    public static void addReqQueue(BaseAppMetaDataDTO baseReqMsg){
        String reqId = UUID.randomUUID().toString().replace("-", "");
        baseReqMsg.setRequestId(reqId);
        reqMsgQueue.add(baseReqMsg);
        requestInfos.putIfAbsent(baseReqMsg.getRequestId(),reqMsgQueue);
    }


    /**
     * 轮询发送消息
     * @para reqId
     * @param channel 连接通道
     */
    public static synchronized void broadcastMess(BaseAppMetaDataDTO reqDTO,Channel channel) {

            try {
                rwLock.readLock().lock();
                String reqId = UUID.randomUUID().toString().replace("-", "");
                reqDTO.setRequestId(reqId);
                channel.writeAndFlush(reqDTO);
                logger.info("服务端发送消息成功: "+reqDTO.toString());


            } finally {
                rwLock.readLock().unlock();
            }
    }


}

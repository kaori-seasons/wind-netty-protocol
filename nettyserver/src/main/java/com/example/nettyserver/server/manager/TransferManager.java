/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.netty.util.internal.StringUtil;

/**
 * @author chengxy
 * 传输实体管理器
 * 2019/10/2
 */
public class TransferManager {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);


    private static ConcurrentHashMap<String,String> chanelInfos = new ConcurrentHashMap<>(); //repid - > channelId 本次用的是哪一个channel进行传输
    private static ConcurrentHashMap<String,Object> userInfos = new ConcurrentHashMap<>(); // repId -> entity 本次传输的对象

    /**
     * 返回本次响应的uuid实体
     */
    public static void addEntity(String repId,Object transferEntity) {
        userInfos.put(repId,transferEntity);
    }


    /**
     * 获取响应的实体
     *
     * @param uid
     */
    public static synchronized void sendRepId(String repId) {
        if (!StringUtil.isNullOrEmpty(repId)) {
            try {
                rwLock.readLock().lock();

            } finally {
                rwLock.readLock().unlock();
            }
        }
    }



}

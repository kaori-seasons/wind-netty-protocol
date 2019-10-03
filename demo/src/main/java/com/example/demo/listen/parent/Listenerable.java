/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.listen.parent;

/**
 * @author chengxy
 * 发送消息的被监听者
 * 2019/10/2
 */
public interface Listenerable {

    public void registerListener(Listener o);
    public void removeListener(Listener o);
    public void notifyListener();
}

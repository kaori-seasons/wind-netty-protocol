/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettycommon.listen.parent;

/**
 * @author chengxy
 * 接收消息的监听者
 * 2019/10/2
 */
public interface Listener<T> {


    int type();

    void update(T message);

}

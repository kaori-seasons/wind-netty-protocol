/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.boostrap;

import com.example.nettyserver.server.listen.PushService;
import com.example.nettyserver.server.remote.NettyJobBeanFactory;

/**
 * @author chengxy
 * 2019/10/2
 */
public class Recevicer {

    PushService pushService = NettyJobBeanFactory.getBean(PushService.class);
}

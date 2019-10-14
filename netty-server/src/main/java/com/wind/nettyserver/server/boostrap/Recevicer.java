/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.boostrap;

import com.hx.nettycommon.dto.parent.BaseReqDTO;
import com.hx.nettyserver.server.remote.NettyJobBeanFactory;

/**
 * @author chengxy
 * 2019/10/2
 */
public class Recevicer {

    BaseReqDTO pushService = NettyJobBeanFactory.getBean(BaseReqDTO.class);
}

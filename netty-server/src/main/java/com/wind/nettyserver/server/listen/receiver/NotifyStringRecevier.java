/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.listen.receiver;

import com.hx.nettycommon.listen.parent.Listener;

import org.springframework.stereotype.Service;

/**
 * @author chengxy
 * 2019/10/10
 */
@Service("notifyAppId")
public class NotifyStringRecevier implements Listener<String> {

    @Override
    public int type() {
        return 1;
    }

    @Override
    public void update(String message) {
        // child class must override this method
    }

}

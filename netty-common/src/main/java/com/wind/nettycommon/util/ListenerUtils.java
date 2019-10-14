/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettycommon.util;

import com.hx.nettycommon.listen.parent.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengxy
 * 监听消息设置
 * 2019/10/10
 */
public final class ListenerUtils {

    public  List<Listener> listener=  new ArrayList<>();

    public  List<Listener> getListener() {
        return listener;
    }

    public void setListener(List<Listener> listener) {
        this.listener = listener;
    }
}

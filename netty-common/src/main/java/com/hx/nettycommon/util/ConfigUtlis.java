/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettycommon.util;

/**
 * @author chengxy
 * 2019/10/9
 */
public final class ConfigUtlis {

    private static String appId;

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        ConfigUtlis.appId = appId;
    }

}

/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettycommon.entity;

import java.io.Serializable;

/**
 * @author chengxy
 * 服务端响应结果
 * 2019/10/2
 */
public class ResponseResult implements Serializable {

    String appId; // 本次客户端返回的id
    String resultCode; // 服务端响应code

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "[ "+"appId = "+appId+" , resultCode"+resultCode+" ]";
    }
}

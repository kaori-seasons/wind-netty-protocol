/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.entity;

import java.io.Serializable;

/**
 * @author chengxy
 * 服务端响应结果
 * 2019/10/2
 */
public class ResponseResult implements Serializable {

    String uid; // 本次客户端返回的id
    String resultCode; // 服务端响应code

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}

/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.util;

import java.io.Serializable;

/**
 * @author chengxy
 * 2019/9/29
 */
public enum  ResponseCodeEnum implements Serializable {

    REQUEST_SUCCESS(10000, "请求成功"),
    SERVER_ERROR(99999, "服务器内部错误"),;

    //region 提供对外访问的方法,无需更改
    /**
     响应码
     */
    private Integer code;
    /**
     响应信息
     */
    private String msg;
    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    //endregion
}

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
 * 数据返回实体封装
 * @author chengxy
 * 2019/9/29
 * @param <T> 通用变量
 */
public class ResponseResult<T> implements Serializable {


    private static final long serialVersionUID = -3411174924856108156L;
    /**
     * 服务器响应码
     */
    private Integer code;
    /**
     * 服务器响应说明
     */
    private String msg;
    /**
     * 服务器响应数据
     */
    private T data;

    public ResponseResult() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseResult<?> that = (ResponseResult<?>) o;

        return (code != null ? code.equals(that.code) : that.code == null) && (msg != null ? msg.equals(that.msg) : that.msg == null) && (data != null ? data.equals(that.data) : that.data == null);
    }

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result
            + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResponseResult{"
            + "code="
            + code
            + ", msg='"
            + msg
            + '\''
            + ", data="
            + data
            + '}';
    }
}

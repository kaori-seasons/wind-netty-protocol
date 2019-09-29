/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.util;

/**
 * @author chengxy
 * 返回结果工具类
 * 2019/9/29
 */
public class ResponseResultUtil {
    /**
     * 请求失败返回的数据结构
     *
     * @param responseCodeEnum 返回信息枚举类
     * @return 结果集
     */
    public static ResponseResult error(ResponseCodeEnum responseCodeEnum) {
        ResponseResult ResponseResult = new ResponseResult();
        ResponseResult.setMsg(responseCodeEnum.getMsg());
        ResponseResult.setCode(responseCodeEnum.getCode());
        ResponseResult.setData(null);
        return ResponseResult;
    }

    /**
     * 没有结果集的返回数据结构
     *
     * @return 结果集
     */
    public static ResponseResult success() {
        return success(null);
    }

    /**
     * 成功返回数据结构
     *
     * @param o 返回数据对象
     * @return 返回结果集
     */
    public static ResponseResult success(Object o) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(ResponseCodeEnum.REQUEST_SUCCESS.getMsg());
        responseResult.setCode(ResponseCodeEnum.REQUEST_SUCCESS.getCode());
        responseResult.setData(o);
        return responseResult;
    }

    /**
     * 判断是否成功
     *
     * @param responseResult 请求结果
     * @return 判断结果
     */
    public static boolean judgementSuccess(ResponseResult responseResult) {
        return responseResult.getCode().equals(ResponseCodeEnum.REQUEST_SUCCESS.getCode());
    }
}

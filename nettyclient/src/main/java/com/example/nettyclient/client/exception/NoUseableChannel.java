/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.exception;

/**
 * @author chengxy
 * 没有可用的通道异常
 * 2019/9/29
 */
public class NoUseableChannel extends RuntimeException {

    private static final long serialVersionUID = 7762465537123947683L;

    public NoUseableChannel() {
        super();
    }

    public NoUseableChannel(String message) {
        super(message);
    }
}

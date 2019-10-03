/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.listen.parent;

/**
 * @author chengxy
 * 2019/10/2
 */
public class MsgRecevicer implements Listener {

    private String name;

    private String message;

    @Override
    public void update(String message) {
        this.message = message;
    }
}

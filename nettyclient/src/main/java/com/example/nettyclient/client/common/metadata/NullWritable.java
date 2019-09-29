/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.metadata;

import java.io.Serializable;

/**
 * @author chengxy
 * 2019/9/29
 */
public class NullWritable implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 2123827169429254101L;
    /**
     * 单例
     */
    private static NullWritable instance = new NullWritable();

    /**
     * 私有构造器
     */
    private NullWritable() {
    }

    /**
     * 返回代表Null的对象
     *
     * @return {@link NullWritable} 当方法返回值为void时或返回值为null时返回此对象
     */
    public static NullWritable nullWritable() {
        return instance;
    }
}

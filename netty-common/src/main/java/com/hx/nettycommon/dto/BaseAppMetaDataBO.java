/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettycommon.dto;

import java.io.Serializable;

import lombok.ToString;

/**
 * @author chengxy
 * 2019/10/9
 */
@ToString(callSuper=true, includeFieldNames=true)
public class BaseAppMetaDataBO implements Serializable {

    private String appId;

    private String entcryStr;

    private boolean flag;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEntcryStr() {
        return entcryStr;
    }

    public void setEntcryStr(String entcryStr) {
        this.entcryStr = entcryStr;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.controller;

import com.hx.nettycommon.dto.parent.BaseAppMetaDataDTO;
import com.hx.nettycommon.util.ConfigUtlis;
import com.hx.nettyserver.server.manager.ChannelManager;

/**
 * @author chengxy
 * 2019/10/10
 */
public class ServerController {

    public static void main(String[] args){
        BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO();
        ChannelManager.broadcastMess(ConfigUtlis.getAppId(),baseAppMetaDataDTO);
    }
}

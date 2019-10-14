/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyserver.server.listen.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.listen.parent.Listener;
import com.hx.nettycommon.util.SymmetricCryptoUtils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chengxy
 * 2019/10/10
 */
@Service("notifyTransferDTO")
@Slf4j
public class NotifyReceiver implements InitializingBean, ApplicationContextAware, Listener<BaseAppMetaDataBO> {


    private ApplicationContext applicationContext;

    private NotifyReceiver receiver;

    @Override
    public final int type() {
        return 0;
    }

    @Override
    public final void update(BaseAppMetaDataBO message) {
        String encrypt = message.getFlag() ? receiver.getKey(message.getAppId()) : SymmetricCryptoUtils.ENCRYPT;
        String decryptStr;

        try {
            decryptStr = SymmetricCryptoUtils.getInstance(encrypt).decryptStr(message.getEntcryStr());
            log.info("receive message body -> {}", decryptStr);
            JSONObject result = JSON.parseObject(decryptStr);
            receiver.doUpdate(message.getAppId(), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doUpdate(String appId, JSONObject result) {
        // subclass must override this method
    }

    protected String getKey(String appId) {
        // subclass must override this method
        return "";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, NotifyReceiver> beanMap = applicationContext.getBeansOfType(NotifyReceiver.class);
        if (null != receiver) {
            return;
        }
        /**
         * 注入子类的实现
         */
        for (NotifyReceiver clazz : beanMap.values()) {
            if (clazz.getClass() == getClass()) {
                continue;
            }
            receiver = clazz;
            break;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

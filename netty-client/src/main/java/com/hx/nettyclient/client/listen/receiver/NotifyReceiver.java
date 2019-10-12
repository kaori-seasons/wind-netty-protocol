/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.hx.nettyclient.client.listen.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hx.nettycommon.dto.BaseAppMetaDataBO;
import com.hx.nettycommon.listen.parent.Listener;
import com.hx.nettycommon.util.SymmetricCryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        String encrypt = message.getFlag() ? getKey(message.getAppId()) : SymmetricCryptoUtils.ENCRYPT;
        String decryptStr = null;
        try {
            //解密为字符串
            decryptStr = SymmetricCryptoUtils.getInstance(encrypt).decryptStr(message.getEntcryStr());
            log.info("receive message body -> {}", decryptStr);
            JSONObject result = JSON.parseObject(decryptStr);
            doUpdate(result);
        } catch (Exception e) {
            log.error("解密异常...");
        }
    }

    protected void doUpdate(JSONObject result) {
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
            log.debug("NotifyReceiver poplute class :{}", clazz.getClass().getName());
            break;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

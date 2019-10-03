/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyserver.server.remote;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chengxy
 * 2019/10/2
 */
@Component
public class NettyJobBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplication(){
        return context;
    }

    public static void clearHolder(){
        context = null;
    }


    public static <T> T getBean(Class<T> requiredType){
        return (T) getApplication().getBean(requiredType);
    }

    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) getApplication().getBean(name);
    }

    //判断application是否为空
    public static void assertContextInjected(){
        Validate.isTrue(context==null, "application未注入 ，请在需要的地方加上注解");
    }

}

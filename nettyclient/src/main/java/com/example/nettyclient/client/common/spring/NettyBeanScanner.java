/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.spring;

import com.example.nettyclient.client.common.remote.RPCProxyFactoryBean;
import com.example.nettyclient.client.common.util.PackageClassUtils;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author chengxy
 * 动态加载代理bean到spring工厂
 * 2019/9/30
 */
public class NettyBeanScanner implements BeanFactoryPostProcessor {


    private DefaultListableBeanFactory beanFactory; //监听注册到容器里面的bean

    private String basePackage;

    private String clientName;

    public NettyBeanScanner(String basePackage, String clientName) {
        this.basePackage = basePackage;
        this.clientName = clientName;
    }

    /**
     * 注册Bean到Spring的bean工厂
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        // 加载远程服务的接口
        List<String> resolverClass = PackageClassUtils.resolver(basePackage);
        for (String clazz : resolverClass) {
            String simpleName;
            if (clazz.lastIndexOf('.') != -1) {
                simpleName = clazz.substring(clazz.lastIndexOf('.') + 1);
            } else {
                simpleName = clazz;
            }
            BeanDefinitionBuilder gd = BeanDefinitionBuilder.genericBeanDefinition(RPCProxyFactoryBean.class);
            gd.addPropertyValue("interfaceClass", clazz);
            gd.addPropertyReference("nettyClient", clientName);
            this.beanFactory.registerBeanDefinition(simpleName, gd.getRawBeanDefinition());
        }
    }

}

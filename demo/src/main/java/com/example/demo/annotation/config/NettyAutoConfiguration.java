/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.annotation.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chengxy
 * 2019/10/1
 */
@EnableConfigurationProperties(NettyConfigProperties.class)
//@Configuration
//@ConditionalOnProperty(prefix = "netty.hxmec", value = "enable")
public class NettyAutoConfiguration {

}

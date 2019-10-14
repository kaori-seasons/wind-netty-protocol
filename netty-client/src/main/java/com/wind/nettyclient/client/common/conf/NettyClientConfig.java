package com.hx.nettyclient.client.common.conf;


import com.hx.nettycommon.annotation.config.EnableNetty;

import org.springframework.context.annotation.Configuration;

@EnableNetty
@Configuration
public  class NettyClientConfig {


    private String localHostaddress;


    private Integer localHostport;


    private String remoteHostAddress;


    private Integer remoteHostPort;

    private String appId;

    private Integer clientWorkerThreadNum;

    public String getLocalHostaddress() {
        return localHostaddress;
    }

    public Integer getLocalHostport() {
        return localHostport;
    }

    public String getRemoteHostAddress() {
        return remoteHostAddress;
    }

    public Integer getRemoteHostPort() {
        return remoteHostPort;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getClientWorkerThreadNum() {
        return clientWorkerThreadNum;
    }

    public void setClientWorkerThreadNum(Integer clientWorkerThreadNum) {
        this.clientWorkerThreadNum = clientWorkerThreadNum;
    }
}

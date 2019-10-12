package com.hx.nettycommon.annotation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "netty.hxmec")
public class NettyConfigProperties {

    private String localHostaddress;

    private Integer localHostport;

    private String remoteHostAddress;

    private Integer remoteHostPort;

    private String appId;

    //服务端配置
    private Integer serverBossThreadNum;

    private Integer serverWorkerThreadNum;

    //客户都安配置
    private Integer clientWorkerThreadNum;


    private Integer soBackLog; // 配置TCP参数
                //.option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区
                //.option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
                //.option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
                //.option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                //.childOption(ChannelOption.SO_KEEPALIVE, true)


    public void setLocalHostaddress(String localHostaddress) {
        this.localHostaddress = localHostaddress;
    }

    public void setLocalHostport(Integer localHostport) {
        this.localHostport = localHostport;
    }

    public void setRemoteHostAddress(String remoteHostAddress) {
        this.remoteHostAddress = remoteHostAddress;
    }

    public void setRemoteHostPort(Integer remoteHostPort) {
        this.remoteHostPort = remoteHostPort;
    }

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

    public Integer getServerBossThreadNum() {
        return serverBossThreadNum;
    }

    public void setServerBossThreadNum(Integer serverBossThreadNum) {
        this.serverBossThreadNum = serverBossThreadNum;
    }

    public Integer getServerWorkerThreadNum() {
        return serverWorkerThreadNum;
    }

    public void setServerWorkerThreadNum(Integer serverWorkerThreadNum) {
        this.serverWorkerThreadNum = serverWorkerThreadNum;
    }

    public Integer getClientWorkerThreadNum() {
        return clientWorkerThreadNum;
    }

    public void setClientWorkerThreadNum(Integer clientWorkerThreadNum) {
        this.clientWorkerThreadNum = clientWorkerThreadNum;
    }
}

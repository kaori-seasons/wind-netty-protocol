package com.hx.nettyserver.server.common;

import com.hx.nettycommon.annotation.config.EnableNetty;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableNetty
public class NettyServerConfig {

    private String localHostaddress="127.0.0.1";


    private Integer localHostport;


    private String remoteHostAddress;


    private Integer remoteHostPort;

    private String appId;

    private Integer serverWorkThread;

    private Integer serverBossThread;


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

    public Integer getServerWorkThread() {
        return serverWorkThread;
    }

    public void setServerWorkThread(Integer serverWorkThread) {
        this.serverWorkThread = serverWorkThread;
    }

    public Integer getServerBossThread() {
        return serverBossThread;
    }

    public void setServerBossThread(Integer serverBossThread) {
        this.serverBossThread = serverBossThread;
    }
}

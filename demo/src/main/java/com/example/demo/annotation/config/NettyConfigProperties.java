package com.example.demo.annotation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "netty.hxmec")
public class NettyConfigProperties {


    private String localHostaddress;


    private Integer localHostport;


    private String remoteHostAddress;


    private Integer remoteHostPort;

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
}

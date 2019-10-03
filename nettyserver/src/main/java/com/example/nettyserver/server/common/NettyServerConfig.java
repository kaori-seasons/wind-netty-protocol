package com.example.nettyserver.server.common;

import com.example.demo.annotation.config.EnableNetty;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableNetty
public class NettyServerConfig {

    private String localHostaddress;


    private Integer localHostport;


    private String remoteHostAddress;


    private Integer remoteHostPort;

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

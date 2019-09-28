package com.example.nettyclient;

import com.example.nettyclient.client.boostrap.TestNettyClient;
import com.example.nettyclient.client.common.conf.NettyClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettyclientApplication implements CommandLineRunner {

    @Autowired
    private TestNettyClient testNettyClient;

    @Autowired
    private NettyClientConfig nettyClientConfig;

    public static void main(String[] args) {
        SpringApplication.run(NettyclientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        InetSocketAddress remorteAddress = new InetSocketAddress(nettyClientConfig.getRemoteHostAddress(),nettyClientConfig.getRemoteHostPort());
        InetSocketAddress localAddress = new InetSocketAddress(nettyClientConfig.getLocalHostaddress(),nettyClientConfig.getLocalHostport());
        testNettyClient.start(remorteAddress,localAddress);
    }
}

package com.example.nettyserver;

import com.example.nettyserver.server.boostrap.TestNettyServer;
import com.example.nettyserver.server.common.NettyServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettyApplication implements CommandLineRunner {


    @Autowired
    private TestNettyServer testNettyServer;

    @Autowired
    private NettyServerConfig nettyServerConfig;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(nettyServerConfig.getLocalHostaddress(),nettyServerConfig.getLocalHostport());
        testNettyServer.start(address);
    }

}

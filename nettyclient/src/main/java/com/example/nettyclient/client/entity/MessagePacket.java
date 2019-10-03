package com.example.nettyclient.client.entity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;;import java.net.InetSocketAddress;

public class MessagePacket {

    private String message;

    private Byte Command;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getCommand() {
        return Command;
    }

    public void setCommand(Byte command) {
        Command = command;
    }
}

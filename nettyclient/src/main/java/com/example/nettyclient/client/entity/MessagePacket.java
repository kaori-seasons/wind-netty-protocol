package com.example.nettyclient.client.entity;

import com.example.nettyclient.client.common.packet.Packet;

;

public class MessagePacket extends Packet {

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

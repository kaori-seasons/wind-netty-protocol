package com.hx.nettyclient.client.entity;

;

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

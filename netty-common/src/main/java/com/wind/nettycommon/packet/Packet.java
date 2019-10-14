package com.hx.nettycommon.packet;

public abstract class Packet {

    //协议版本
    private Byte version = 1;

    //指令
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}

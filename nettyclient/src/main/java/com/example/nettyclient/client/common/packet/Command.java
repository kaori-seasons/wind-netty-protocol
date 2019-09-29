package com.example.nettyclient.client.common.packet;

/**
 * 状态指令
 *
 */
public interface Command {


    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte ORDER_MESSAGE_REQUEST = 3;

    Byte ORDER_MESSAGE_RESPONSE = 4;

}

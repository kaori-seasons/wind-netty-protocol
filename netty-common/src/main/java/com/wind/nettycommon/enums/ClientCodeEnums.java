package com.hx.nettycommon.enums;

/**
 * <p>
 * Client 请求/响应码 枚举
 * </p>
 *
 * @author luocj
 * @date 2019/10/8
 */
public enum ClientCodeEnums {

    PUSH_HEART(10001,"健康检查请求"),
    PULL_AGENT_KEY(10002,"请求Agent密钥"),
    PULL_WATCHDOG_KEY(10003,"请求WatchDog密钥"),
    PUSH_LOG(10004,"日志推送"),
    RES_AGENT_UPGRADE(10005,"Agent升级响应"),
    RES_WATCHDOG_UPGRADE(10006,"WatchDog升级响应"),
    ;

    private int code;
    private String desc;

    ClientCodeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

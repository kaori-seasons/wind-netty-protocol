package com.hx.nettycommon.enums;

/**
 * <p>
 * Monitor 请求/响应码 枚举
 * </p>
 *
 * @author luocj
 * @date 2019/10/8
 */
public enum ServerCodeEnums {

    RES_HEART(20001,"健康检查响应"),
    PUSH_AGENT_KEY(20002,"推送Agent密钥"),
    PUSH_WATCHDOG_KEY(20003,"推送WatchDog密钥"),
    RES_LOG(20004,"client日志推送响应"),
    PUSH_AGENT_UPGRADE(20005,"推送Agent升级"),
    PUSH_WATCHDOG_UPGRADE(20006,"推送WatchDog升级"),
    ;


    private int code;

    private String desc;

    ServerCodeEnums(int code, String desc) {
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

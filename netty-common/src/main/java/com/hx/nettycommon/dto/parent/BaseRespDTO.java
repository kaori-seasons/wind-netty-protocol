package com.hx.nettycommon.dto.parent;


import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * Netty响应基类
 * </p>
 *
 * @author luocj
 * @date 2019/10/8
 */
@Data
public class BaseRespDTO extends BaseAppMetaDataDTO implements Serializable {

    private static final long serialVersionUID = -4939215423910039289L;

    /**
     * 响应码 code
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String msg;
}

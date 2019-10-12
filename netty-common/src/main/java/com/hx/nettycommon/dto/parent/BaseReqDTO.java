package com.hx.nettycommon.dto.parent;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * Netty请求基类
 * </p>
 *
 * @author luocj
 * @date 2019/10/8
 */
@Data
public class BaseReqDTO extends BaseAppMetaDataDTO implements Serializable {

    private static final long serialVersionUID = -1953053275664609315L;

    /**
     * 请求码 code
     */
    private int code;

    /**
     * 应用唯一身份id
     */
    private String appId;



}

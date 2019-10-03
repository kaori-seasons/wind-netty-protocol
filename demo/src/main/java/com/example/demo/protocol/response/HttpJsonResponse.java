/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.demo.protocol.response;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author chengxy
 * 2019/9/30
 */
public class HttpJsonResponse {

    private FullHttpResponse httpResponse;
    private Object result;

    public HttpJsonResponse(FullHttpResponse httpResponse, Object result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }

    /**
     * @return the httpResponse
     */
    public final FullHttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * @param httpResponse the httpResponse to set
     */
    public final void setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * @return the body
     */
    public final Object getResult() {
        return result;
    }

    /**
     * @param body the body to set
     */
    public final void setResult(Object result) {
        this.result = result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HttpJsonResponse [httpResponse=" + httpResponse + ", result="
            + result + "]";
    }
}

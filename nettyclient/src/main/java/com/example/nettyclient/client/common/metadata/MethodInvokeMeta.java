/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.example.nettyclient.client.common.metadata;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author chengxy
 * 2019/9/29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MethodInvokeMeta implements Serializable {

    private static final long serialVersionUID = 5429914235135594820L;
    /**
     * 接口
     */
    private Class<?> interfaceClass;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private Object[] args;
    /**
     * 返回值类型
     */
    private Class<?> returnType;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }
}

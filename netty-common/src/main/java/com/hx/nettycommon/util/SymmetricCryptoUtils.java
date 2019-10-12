package com.hx.nettycommon.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author cj.luo
 * @date 2019/10/11
 */
public class SymmetricCryptoUtils {

    public static final String ENCRYPT = "a369862ae7e94ac1ad2437343264ef85";

    /**
     * 返回加密实例
     *
     * @param encrypt
     * @return
     */
    public static SymmetricCrypto getInstance(String encrypt) {
        if (null == encrypt) {
            encrypt = ENCRYPT;
        }
        return new SymmetricCrypto(SymmetricAlgorithm.DESede, encrypt.getBytes());
    }
}

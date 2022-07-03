package com.AuD.common.function.crypto;

/**
 * Description: 加密异常
 *
 * @author AuD/胡钊
 * @ClassName CryptoException
 * @date 2021/11/29 16:35
 * @Version 1.0
 */
public class CryptoException extends RuntimeException {

    public CryptoException(String message,Throwable throwable){
        super(message,throwable);
    }

}

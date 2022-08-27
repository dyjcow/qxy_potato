package com.qxy.potatos.base;

import java.io.IOException;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:32
 * @description：Exception的base类
 * @modified By：
 * @version: 1.0
 */
public class BaseException extends IOException {


    private String errorMsg;
    private int errorCode;

    public BaseException(String message) {
        this.errorMsg = message;
    }

    public BaseException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
    }


    public BaseException(int errorCode, String message) {
        this.errorMsg = message;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

}

package com.qxy.potato.base;


import java.io.Serializable;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 10:11
 * @description：bean的base类
 * @modified By：
 * @version: 1.0
 */
public class BaseBean<T> implements Serializable {



    //From 测试图片
    public int code;
    public String msg;

    public String message;

    public T data;


    public BaseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
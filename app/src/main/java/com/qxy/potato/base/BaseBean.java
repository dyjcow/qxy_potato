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


    /**
     * data :
     * errorCode : 0
     * errorMsg :
     */

    public int code;
    public String fxLink;
    public String msg;
    public String updateTime;
    public String sunrise;
    public String sunset;
    public String moonrise;
    public String moonset;
    public T data;
    public T topCityList;
    public T location;
    public T daily;
    public T hourly;
    public T now;


    public BaseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
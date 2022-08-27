package com.qxy.potatos.base;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/10 0:37
 * @description：EventBus发送的信息的基类
 * @modified By：
 * @version: 1.0
 */
public class BaseEvent<T> {
    private int eventCode;

    private T data;


    public BaseEvent(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

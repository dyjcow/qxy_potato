package com.qxy.potato.util;

import com.qxy.potato.base.BaseEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/10 0:35
 * @description：封装EventBus工具类
 * @modified By：
 * @version: 1.0
 */
public class EventBusUtil {

    /**
     * 发送消息(事件)
     *
     * @param event
     */
    public static void sendEvent(BaseEvent<?> event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送 粘性 事件
     * <p>
     * 粘性事件，在注册之前便把事件发生出去，等到注册之后便会收到最近发送的粘性事件（必须匹配）
     * 注意：只会接收到最近发送的一次粘性事件，之前的会接受不到。
     *
     * @param event
     */
    public static void sendStickyEvent(BaseEvent<?> event) {
        EventBus.getDefault().postSticky(event);
    }

}

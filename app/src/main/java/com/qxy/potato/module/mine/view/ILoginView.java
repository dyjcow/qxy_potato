package com.qxy.potato.module.mine.view;

import com.qxy.potato.base.BaseView;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/13 16:07
 * @description：登录接口
 * @modified By：
 * @version: 1.0
 */
public interface ILoginView extends BaseView {


    /**
     * 请求登录授权
     * @return 返回是否打开sdk调用成功
     */
    boolean sendAuth();
}

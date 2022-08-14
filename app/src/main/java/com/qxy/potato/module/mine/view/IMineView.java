package com.qxy.potato.module.mine.view;

import com.qxy.potato.base.BaseView;


/**
 * @author ：Dyj
 * @date ：Created in 2022/8/11 22:37
 * @description：个人中心的View层接口
 * @modified By：
 * @version: 1.0
 */
public interface IMineView extends BaseView {

    /**
     * 成功登录的操作
     */
    void loginSuccess();

    /**
     * 载入照片
     */
    void loadImg(String url);

    /**
     * 登录失败的操作
     */
    void loginFailed(String msg);

    void cancelClientValue();
}

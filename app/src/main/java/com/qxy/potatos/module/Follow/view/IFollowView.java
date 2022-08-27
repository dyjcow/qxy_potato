package com.qxy.potatos.module.Follow.view;

import com.qxy.potatos.base.BaseView;
import com.qxy.potatos.bean.Fans;
import com.qxy.potatos.bean.Followings;

/**
 * @author ：potato
 * @date ：Created in 2022/8/18 22:55
 * @description：关注粉丝接口
 * @modified By：
 * @version: 1.0
 */
public interface IFollowView extends BaseView {

    void showFollowingsList(Followings follow);

    void showFansList(Fans followers);

    void loadFail(String msg);
}

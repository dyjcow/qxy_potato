package com.qxy.potato.module.home.view;

import com.qxy.potato.base.BaseView;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.bean.UserInfo;
import com.qxy.potato.bean.VideoList;

import java.util.List;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.view
 * @date :2022/8/16 21:23
 */
public interface IHomeView extends BaseView {
    void showPersonalInfo(UserInfo userInfo);

    void showPersonalVideo(List<MyVideo.Videos> videos,boolean isHasMore,long cursor);


    /**
     * 成功登录的操作
     */
    void loginSuccess();

    /**
     * 登录失败的操作
     */
    void loginFailed(String msg);

    void cancelClientValue();
}

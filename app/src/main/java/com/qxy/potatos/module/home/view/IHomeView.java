package com.qxy.potatos.module.home.view;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;

import com.qxy.potatos.base.BaseView;
import com.qxy.potatos.bean.MyVideo;
import com.qxy.potatos.bean.UserInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.view
 * @date :2022/8/16 21:23
 */
public interface IHomeView extends BaseView {
    void showPersonalInfo(UserInfo userInfo);

    void showPersonalVideo(List<MyVideo.Videos> videos, boolean isHasMore, long cursor);

    void startWork(long duration, @NonNull TimeUnit timeUnit,
                   String tag, @NonNull Class<? extends ListenableWorker> workerClass);
}

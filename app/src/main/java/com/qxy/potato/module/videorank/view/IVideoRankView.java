package com.qxy.potato.module.videorank.view;

import com.qxy.potato.base.BaseView;
import com.qxy.potato.bean.VideoList;

import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 16:23
 * @description：榜单的view层
 * @modified By：
 * @version: 1.0
 */
public interface IVideoRankView extends BaseView {


    /**
     * 展示榜单
     *
     * @param videoList 传入的影视List
     */
    void showRankSuccess(VideoList videoList);

    /**
     * 错误信息提示
     *
     * @param errorMsg 错误信息
     */
    void showRankFailed(String errorMsg);

}

package com.qxy.potatos.module.videorank.view;

import com.qxy.potatos.base.BaseView;
import com.qxy.potatos.bean.VideoList;

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
    void showRankSuccess(VideoList videoList, int version);

    /**
     * 错误信息提示
     *
     * @param errorMsg 错误信息
     */
    void showRankFailed(String errorMsg);

}

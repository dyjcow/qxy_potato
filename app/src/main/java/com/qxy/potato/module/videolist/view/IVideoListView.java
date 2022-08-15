package com.qxy.potato.module.videolist.view;

import com.qxy.potato.base.BaseView;
import com.qxy.potato.bean.VideoList;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/14 10:50
 * @description：VideoList接口类
 * @modified By：
 * @version: 1.0
 */
public interface IVideoListView extends BaseView {

	/**
	 * 获取榜单成功展示
	 */
	void showRank(VideoList videoList);

	/**
	 * 获取榜单失败，提醒用户原因
	 */
	void getRankFailed(String msg);


}

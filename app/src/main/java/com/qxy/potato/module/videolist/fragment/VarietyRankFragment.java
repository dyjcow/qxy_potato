package com.qxy.potato.module.videolist.fragment;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.databinding.RankBinding;
import com.qxy.potato.module.videolist.presenter.RankPresenter;
import com.qxy.potato.module.videolist.rank.rankRecyclerViewAdapter;
import com.qxy.potato.module.videolist.view.IVideoListView;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;

/**
 * @author Soul Mate
 * @brief 综艺榜碎片
 * @date 2022-08-14 14:10
 */

public class VarietyRankFragment extends BaseFragment<RankPresenter, RankBinding> implements IVideoListView {

	//我的榜单类型 * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
	private static final int TYPE = 1;

	private rankRecyclerViewAdapter mAdapter = new rankRecyclerViewAdapter(getContext());

	//榜单更新时间
	private TextView mTime;


	private RecyclerView mRecyclerView;



	@Override protected RankPresenter createPresenter() {
		return new RankPresenter(this);
	}

	@Override protected void initView() {

		mTime = getBinding().textviewRankRule;

		mRecyclerView = getBinding().recyclerview;
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setAdapter(mAdapter);

		//设置点击事件
		//返回
		getBinding().imagebuttonBack.setOnClickListener(v -> {
			ActivityUtil.finishActivity(ActivityUtil.getCurrentActivity());
		});
		//分享
		getBinding().imagebuttonShare.setOnClickListener(v->{});
		//榜单规则
		getBinding().textviewRankRule.setOnClickListener(v->{});

		//改背景图
		getBinding().imageviewBackground.setImageResource(R.mipmap.variety_rank);

	}

	@Override protected void initData() {

		showLoading();
		//第一次获取本周的榜单
		presenter.getNowRank(TYPE);


	}


	@Override public void showRank(VideoList videoList) {

		SuccessHideLoading();

		//更新时间
		mTime.setText("本周榜|更新于 "+videoList.getActive_time());

		//更新数据
		mAdapter.setList(videoList.getList());
//		mRecyclerView.notify();


	}

	@Override public void getRankFailed(String msg) {

		FailedHideLoading();

		ToastUtil.showToast(msg);
		LogUtil.i("错误原因："+msg);

	}


}

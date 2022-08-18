package com.qxy.potato.module.videolist.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.databinding.CoordinatorRankBackgroundBinding;
import com.qxy.potato.module.videolist.presenter.RankPresenter;
import com.qxy.potato.module.videolist.rank.MyItemDecoration;
import com.qxy.potato.module.videolist.rank.rankRecyclerViewAdapter;
import com.qxy.potato.module.videolist.view.IVideoListView;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.ToastUtil;

/**
 * @author Soul Mate
 * @brief 综艺榜碎片
 * @date 2022-08-14 14:10
 */

public class VarietyRankFragment extends BaseFragment<RankPresenter, CoordinatorRankBackgroundBinding> implements IVideoListView {

	//我的榜单类型 * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
	private static final int TYPE = 3;

	private rankRecyclerViewAdapter mAdapter ;

	//折叠式标题
	private CollapsingToolbarLayout toolbarLayout;
	private Toolbar toolbar;
	private ImageView background;

	//榜单更新时间
	private TextView mTime;

	private RecyclerView mRecyclerView;

	@Override protected RankPresenter createPresenter() {
		return new RankPresenter(this);
	}

	@Override protected void initView() {

		toolbarLayout = getBinding().collapsingtoolbar;
		toolbar = getBinding().toolbar;
		background = getBinding().rankBackground;
		AppCompatActivity activity=(AppCompatActivity) ActivityUtil.getCurrentActivity();
		activity.setSupportActionBar(toolbar);
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null)
			actionBar.setDisplayHomeAsUpEnabled(true);
		toolbarLayout.setTitle("综艺榜");
		Glide.with(this).load(MyUtil.getString(R.string.pic)).into(background);

		mTime = getBinding().textviewRankTime;

		mAdapter = new rankRecyclerViewAdapter(getContext(),TYPE);
		mRecyclerView = getBinding().recyclerview;
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.addItemDecoration(new MyItemDecoration(getContext()));
		mRecyclerView.setAdapter(mAdapter);

		//设置点击事件
		//榜单规则
		getBinding().textviewRankRule.setOnClickListener(v->{});

	}

	@Override protected void initData() {

		showLoading();
		//第一次获取本周的榜单
		presenter.getNowRank(TYPE);


	}


	@Override public void showRank(VideoList videoList) {


		//更新时间
		mTime.setText("本周榜|更新于 "+videoList.getActive_time());

		//更新数据
		mAdapter.setData(videoList.getList());




	}

	@Override public void getRankFailed(String msg) {


		ToastUtil.showToast(msg);
		LogUtil.i("错误原因："+msg);

	}

}

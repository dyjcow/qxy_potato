package com.qxy.potato.module.videolist.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.qxy.potato.R;
import com.qxy.potato.module.videolist.Adapter.RankPageAdapter;
import com.qxy.potato.module.videolist.fragment.FilmRankFragment;
import com.qxy.potato.module.videolist.fragment.TeleplayRankFragment;
import com.qxy.potato.module.videolist.fragment.VarietyRankFragment;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soul Mate
 * @brief 普通榜单活动
 * @date 2022-08-14 14:10
 */
public class RankActivity extends AppCompatActivity  {

	private ViewPager mViewPager;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);

		initUI();
	}

	/**
	 * 初始化ui界面
	 */
	public void initUI(){

		mViewPager = findViewById(R.id.vp_rank_switch);

		List<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(new FilmRankFragment());
		fragmentList.add(new TeleplayRankFragment());
		fragmentList.add(new VarietyRankFragment());

		//设置Title
		List<String> titleList = new ArrayList<>();
		titleList.add("电影榜");
		titleList.add("剧集榜");
		titleList.add("综艺榜");

		//ViewPager设置适配器
		mViewPager.setAdapter(new RankPageAdapter(getSupportFragmentManager(), fragmentList,titleList));


	}




}
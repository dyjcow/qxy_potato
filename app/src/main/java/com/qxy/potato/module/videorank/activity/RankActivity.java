package com.qxy.potato.module.videorank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.base.BaseView;
import com.qxy.potato.databinding.ActivityVideoRankBinding;
import com.qxy.potato.module.videorank.adapter.VRViewPageAdapter;
import com.qxy.potato.module.videorank.fragment.VideoRankFragment;
import com.qxy.potato.module.videorank.presenter.RankPresenter;
import com.qxy.potato.module.videorank.presenter.VideoRankPresenter;
import com.qxy.potato.module.videorank.view.IRankView;
import com.qxy.potato.util.ColorUtil;
import com.qxy.potato.util.DisplayUtil;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.ToastUtil;
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity<RankPresenter, ActivityVideoRankBinding> implements IRankView {
    private final String[] tabText = {"电影榜", "电视剧榜", "综艺榜"};


    /**
     * 初始化presenter，也是与Activity的绑定
     *
     * @return 返回new的Presenter层的值
     */
    @Override
    protected RankPresenter createPresenter() {
        return new RankPresenter(this);
    }

    /**
     * 载入view的一些操作
     */
    @Override
    protected void initView() {
        Glide.with(this)
                .load("https://www.lxtlovely.top/getpic.php")
                .into(getBinding().rankBackground);
        showView();
    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {

    }

    private void showView() {
        List<VideoRankFragment> fragmentList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            VideoRankFragment rankFragment = new VideoRankFragment(i);
            fragmentList.add(rankFragment);
        }
        VRViewPageAdapter adapter = new VRViewPageAdapter(this, fragmentList);
        getBinding().viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        getBinding().viewPager2.setAdapter(adapter);
        new TabLayoutMediator(getBinding().tabLayout, getBinding().viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabText[position]);
            }
        }).attach();

    }
}
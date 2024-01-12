package com.qxy.potatos.module.videorank.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.potatos.R;
import com.qxy.potatos.annotation.InitAIHand;
import com.qxy.potatos.base.BaseActivity;
import com.qxy.potatos.databinding.ActivityVideoRankBinding;
import com.qxy.potatos.module.videorank.adapter.VRViewPageAdapter;
import com.qxy.potatos.module.videorank.fragment.VideoRankFragment;
import com.qxy.potatos.module.videorank.presenter.RankPresenter;
import com.qxy.potatos.module.videorank.view.IRankView;
import com.qxy.potatos.util.ActivityUtil;
import com.qxy.potatos.util.ColorUtil;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

@InitAIHand
public class RankActivity extends BaseActivity<RankPresenter, ActivityVideoRankBinding> implements IRankView {
    private final String[] tabText = {"电影榜", "电视剧榜", "综艺榜"};
    private final Activity activity = ActivityUtil.getCurrentActivity();

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
        // 关闭预加载
        getBinding().viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);  // 可以不设置 因为默认是 -1 默认不进行预加载
        // 这个必须设置 不然仍然会启用预加载
        ((RecyclerView)getBinding().viewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
        // 设置缓存数量，对应 RecyclerView 中的 mCachedViews，即屏幕外的视图数量,此处设置为2
        ((RecyclerView)getBinding().viewPager2.getChildAt(0)).setItemViewCacheSize(2);
        getBinding().viewPager2.setAdapter(adapter);
        //联动viewPage和TabLayout
        new TabLayoutMediator(getBinding().tabLayout, getBinding().viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(activity);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setText(tabText[position]);
                tab.setCustomView(textView);
            }
        }).attach();

        getBinding().viewPager2.registerOnPageChangeCallback(changeCallback);
        getBinding().tabLayout.addOnTabSelectedListener(selectedListener);

    }


    //根据标签选中与否更改文本效果
    private TabLayout.OnTabSelectedListener selectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getCustomView() != null) {
                TextView textView = (TextView) tab.getCustomView();
                textView.setTextAppearance(activity, R.style.selected_tab_text);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (tab.getCustomView() != null) {
                TextView textView = (TextView) tab.getCustomView();
                textView.setTextAppearance(activity, R.style.unselected_tab_text);
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    //根据页面切换与否更改文本效果
    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            int tabCount = getBinding().tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = getBinding().tabLayout.getTabAt(i);
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    if (tab.getPosition() == position) {
                        textView.setTextAppearance(activity, R.style.selected_tab_text);
                    } else {
                        textView.setTextAppearance(activity, R.style.unselected_tab_text);
                    }
                }
            }
        }
    };
}
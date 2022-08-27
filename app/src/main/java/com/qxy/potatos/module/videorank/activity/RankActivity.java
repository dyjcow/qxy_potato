package com.qxy.potatos.module.videorank.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseActivity;
import com.qxy.potatos.databinding.ActivityVideoRankBinding;
import com.qxy.potatos.module.videorank.adapter.VRViewPageAdapter;
import com.qxy.potatos.module.videorank.fragment.VideoRankFragment;
import com.qxy.potatos.module.videorank.presenter.RankPresenter;
import com.qxy.potatos.module.videorank.view.IRankView;

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
                tab.setText(tabText[position]);
            }
        }).attach();

    }
}
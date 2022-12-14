package com.qxy.potatos.module.Follow.fragment;

import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseFragment;
import com.qxy.potatos.base.BasePresenter;
import com.qxy.potatos.base.BaseView;
import com.qxy.potatos.databinding.RelativelayoutMineFollowBinding;
import com.qxy.potatos.module.Follow.adapter.FollowPagerAdapter;
import com.qxy.potatos.util.ActivityUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author potato
 * @brief 关注粉丝页面
 * @date 2022-08-17 23:45
 */

public class FollowFragment extends BaseFragment<BasePresenter, RelativelayoutMineFollowBinding> implements BaseView {

    public static final int FOLLOWINGS = 0;
    public static final int Fans = 1;
    //返回
    private ImageButton back;
    //tabLayout
    private TabLayout tabLayout;
    //ViewPager
    private ViewPager2 viewPager;
    //绑定ViewPager和TabLayout
    private TabLayoutMediator mediator;
    private Activity activity = ActivityUtil.getCurrentActivity();
    private int mType;
    private TabLayoutMediator.TabConfigurationStrategy strategy = (tab, position) -> {
        TextView textView = new TextView(activity);
        textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        //设置文本内容
        switch (position) {
            case 0:
                textView.setText(R.string.followings);
                break;
            case 1:
                textView.setText(R.string.fans);
            default:
                break;
        }
        tab.setCustomView(textView);
    };

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
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
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

    //获取默认启动标签页面
    public FollowFragment(int type) {
        super();
        mType = type;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(this);
    }

    @Override
    protected void initView() {
        //退出碎片
        back = getBinding().imagebuttonMineBack;
        back.setOnClickListener(v -> activity.onBackPressed());

        tabLayout = getBinding().tablelayoutMine;
        viewPager = getBinding().viewpagerMine;

        AppCompatActivity compatActivity = (AppCompatActivity) activity;

        //加入关注和粉丝碎片，传入type类型来生成不同碎片
        List<FollowListFragment> list = Arrays.asList(new FollowListFragment(FOLLOWINGS), new FollowListFragment(Fans));
        viewPager.setAdapter(new FollowPagerAdapter(compatActivity.getSupportFragmentManager(), compatActivity.getLifecycle(), list));
        // 关闭预加载
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);  // 可以不设置 因为默认是 -1 默认不进行预加载
        // 这个必须设置 不然仍然会启用预加载
        ((RecyclerView)viewPager.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
        // 设置缓存数量，对应 RecyclerView 中的 mCachedViews，即屏幕外的视图数量,此处设置为2
        ((RecyclerView)viewPager.getChildAt(0)).setItemViewCacheSize(1);
        //tabLayout与viewPager绑定
        mediator = new TabLayoutMediator(tabLayout, viewPager, strategy);
        mediator.attach();

        //选中标签,字体加粗
        viewPager.registerOnPageChangeCallback(changeCallback);
        tabLayout.addOnTabSelectedListener(selectedListener);

        //设置默认启动页面
        if (mType == Fans)
            tabLayout.getTabAt(Fans).select();
    }

    @Override
    protected void initData() {

    }
}

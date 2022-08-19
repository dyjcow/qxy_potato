package com.qxy.potato.module.mine.fragment;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.base.BaseView;
import com.qxy.potato.databinding.RelativelayoutMineFollowBinding;
import com.qxy.potato.module.mine.adapter.FollowPagerAdapter;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author potato
 * @brief 关注粉丝页面
 * @date 2022-08-17 23:45
 */

public class FollowFragment extends BaseFragment<BasePresenter, RelativelayoutMineFollowBinding> implements BaseView {

    //返回
    private ImageButton back;

    //tabLayout
    private TabLayout tabLayout;

    //ViewPager
    private ViewPager2 viewPager;

    //绑定ViewPager和TabLayout
    private TabLayoutMediator mediator;

    private Activity activity=ActivityUtil.getCurrentActivity();


    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(this);
    }

    @Override
    protected void initView() {
        //退出碎片
        back = getBinding().imagebuttonMineBack;
        back.setOnClickListener(v -> ActivityUtil.getCurrentActivity().onBackPressed());

        tabLayout = getBinding().tablelayoutMine;
        viewPager = getBinding().viewpagerMine;

        AppCompatActivity compatActivity = (AppCompatActivity) activity;

        //加入关注和粉丝碎片
        List<FollowListFragment> list = Arrays.asList(new FollowListFragment(), new FollowListFragment());
        viewPager.setAdapter(new FollowPagerAdapter(compatActivity.getSupportFragmentManager(), compatActivity.getLifecycle(), list));

        //绑定
        mediator = new TabLayoutMediator(tabLayout, viewPager,strategy);
        mediator.attach();

        //选中标签,字体加粗
        viewPager.registerOnPageChangeCallback(changeCallback);
        tabLayout.addOnTabSelectedListener(selectedListener);
    }

    private TabLayoutMediator.TabConfigurationStrategy strategy= (tab, position) -> {
        TextView textView = new TextView(activity);
        textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        switch (position) {
            case 0:
                textView.setText(R.string.follow);
                break;
            case 1:
                textView.setText(R.string.followers);
            default:
                break;
        }
        tab.setCustomView(textView);
    };

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

    @Override
    protected void initData() {

    }
}

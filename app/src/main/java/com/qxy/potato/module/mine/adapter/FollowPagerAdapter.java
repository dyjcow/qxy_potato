package com.qxy.potato.module.mine.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qxy.potato.module.mine.fragment.FollowListFragment;
import com.qxy.potato.module.mine.fragment.FollowFragment;

import java.util.List;

/**
 * @author potato
 * @brief 关注粉丝页面PagerAdapter
 * @date 2022-08-17 23:45
 */
public class FollowPagerAdapter extends FragmentStateAdapter {

    List<FollowListFragment> list;

    public FollowPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<FollowListFragment> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

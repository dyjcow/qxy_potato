package com.qxy.potatos.module.mine.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qxy.potatos.module.mine.fragment.VideoDisplayragment;

import java.util.List;


/**
 * @author Soul Mate
 * @brief 装载webview的碎片的adapter
 * @date 2022-08-24 17:37
 */

public class WVFragmentAdapter extends FragmentStateAdapter{

    private List<String> url;

    public WVFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<String> url) {
        super(fragmentActivity);
        this.url = url;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return new VideoDisplayragment(url.get(position));
    }

    @Override
    public int getItemCount() {
        return url.size();
    }
}

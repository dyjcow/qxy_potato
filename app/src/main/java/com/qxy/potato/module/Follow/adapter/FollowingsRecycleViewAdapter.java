package com.qxy.potato.module.Follow.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potato.R;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.util.ActivityUtil;

import java.util.List;

public class FollowingsRecycleViewAdapter extends BaseQuickAdapter<Followings.Following, BaseViewHolder> {
    public FollowingsRecycleViewAdapter(@Nullable List<Followings.Following> data) {
        super(R.layout.recyclerview_item_follow, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Followings.Following following) {
        baseViewHolder.setText(R.id.address, following.getCountry() + following.getProvince() + following.getCity());
        //加载昵称
        baseViewHolder.setText(R.id.nickname, following.getNickname());
        //加载头像
        ImageView avatar = baseViewHolder.getView(R.id.avatar);
        Glide.with(ActivityUtil.getCurrentActivity()).load(following.getAvatar()).into(avatar);
        ImageView gender = baseViewHolder.getView(R.id.gender);
        switch (following.getGender()) {
            case 0: {
                Glide.with(ActivityUtil.getCurrentActivity()).load(R.mipmap.home_man).into(gender);
                break;
            }
            case 1: {
                Glide.with(ActivityUtil.getCurrentActivity()).load(R.mipmap.home_woman).into(gender);
                break;
            }
            default:
                break;
        }
    }
}

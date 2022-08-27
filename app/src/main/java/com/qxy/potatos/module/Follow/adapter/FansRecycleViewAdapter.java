package com.qxy.potatos.module.Follow.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potatos.R;
import com.qxy.potatos.bean.Fans;
import com.qxy.potatos.util.ActivityUtil;

import java.util.List;

public class FansRecycleViewAdapter extends BaseQuickAdapter<Fans.Fan, BaseViewHolder> {

    public FansRecycleViewAdapter(@Nullable List<Fans.Fan> data) {
        super(R.layout.recyclerview_item_follow, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Fans.Fan fan) {
        baseViewHolder.setText(R.id.address, fan.getCountry() + fan.getProvince() + fan.getCity());
        baseViewHolder.setText(R.id.nickname, fan.getNickname());
        baseViewHolder.setText(R.id.is_connect, "关注了你");
        ImageView avatar = baseViewHolder.getView(R.id.avatar);
        Glide.with(ActivityUtil.getCurrentActivity()).load(fan.getAvatar()).into(avatar);
        ImageView gender = baseViewHolder.getView(R.id.gender);
        switch (fan.getGender()) {
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

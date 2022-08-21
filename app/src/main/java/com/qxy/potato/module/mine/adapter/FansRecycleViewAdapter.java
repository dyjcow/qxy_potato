package com.qxy.potato.module.mine.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potato.R;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.MyUtil;

import java.util.List;

public class FansRecycleViewAdapter extends BaseQuickAdapter<Fans.Fan, BaseViewHolder> {

    public FansRecycleViewAdapter(@Nullable List<Fans.Fan> data) {
        super(R.layout.recyclerview_item_follow, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Fans.Fan fan) {
        baseViewHolder.setText(R.id.nickname,fan.getNickname());
        ImageView avatar=baseViewHolder.getView(R.id.avatar);
        Glide.with(ActivityUtil.getCurrentActivity()).load(fan.getAvatar()).into(avatar);
    }
}

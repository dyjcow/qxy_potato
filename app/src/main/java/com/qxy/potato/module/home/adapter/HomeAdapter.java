package com.qxy.potato.module.home.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potato.R;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.common.GlobalConstant;
import com.tencent.mmkv.MMKV;

import java.util.List;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.adapter
 * @date :2022/8/16 21:29
 */
public class HomeAdapter extends BaseQuickAdapter<MyVideo.Videos, BaseViewHolder> {


    public HomeAdapter(int layoutResId, List<MyVideo.Videos> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, MyVideo.Videos myVideo) {
        //holder.setText(R.id.home_textView_like,"获赞");
        holder.setVisible(R.id.home_textView_Top, myVideo.isIs_top());
        holder.setText(R.id.playCount, myVideo.getStatistics().getPlay_count() + "");
        holder.setText(R.id.likeCount, myVideo.getStatistics().getDigg_count() + "");
        Glide.with(getContext()).load(myVideo.getCover()).into((ImageView) holder.getView(R.id.home_item_imageView));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }


}

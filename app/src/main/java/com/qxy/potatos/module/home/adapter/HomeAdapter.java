package com.qxy.potatos.module.home.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potatos.R;
import com.qxy.potatos.bean.MyVideo;

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
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(getContext())
                .load(myVideo.getCover())
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into((ImageView) holder.getView(R.id.home_item_imageView));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }


}

package com.qxy.potato.module.videorank.adapter;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dylanc.viewbinding.brvah.BaseViewHolderUtilKt;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.databinding.RecyclerviewItemRankBinding;
import com.qxy.potato.util.ActivityUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 20:42
 * @description：Rv适配器
 * @modified By：
 * @version: 1.0
 */
public class VideoRVAdapter extends BaseQuickAdapter<VideoList.Video, BaseViewHolder> {

    public VideoRVAdapter(int layoutResId, @Nullable List<VideoList.Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, VideoList.Video video) {
        RecyclerviewItemRankBinding binding = BaseViewHolderUtilKt
                .getBinding(baseViewHolder, RecyclerviewItemRankBinding::bind);
        Glide.with(ActivityUtil.getCurrentActivity())
                .load(video.getPoster())
                .into(binding.imageViewIcon);
        binding.textViewName.setText(video.getName());
        binding.textViewPopDegree.setText(getNumber(video.getHot()));
        binding.textViewReleaseTime.setText(video.getRelease_date() + " 上映");
        if (video.getTags() == null) {
            binding.textViewType.setText("[国语]");
        } else {
            binding.textViewType.setText(video.getTags() + "");
        }
        binding.textViewScore.setText("暂无评分");
        if (video.getType() == 1) {
            binding.buttonGetTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(), "无法购票", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            binding.buttonGetTicket.setVisibility(View.GONE);
        }
    }

    private String getNumber(int num) {
        float n = (float) num / 10000;
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(n) + "万";
    }
}

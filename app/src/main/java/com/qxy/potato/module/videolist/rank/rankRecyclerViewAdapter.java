package com.qxy.potato.module.videolist.rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.potato.R;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.databinding.RecyclerviewItemRankBinding;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinxiaolong
 * @describe :recyclerViewAdapter
 * @data: :2022/8/11
 */
public class rankRecyclerViewAdapter extends RecyclerView.Adapter<rankRecyclerViewAdapter.MyViewHolder> {
    //此处的范型改为需要的bean类
    private List<VideoList.Video> list = new ArrayList<>();

    private Context mContext;

    public rankRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    /**.
     * 更换数据源
     */
    public void setList(List<VideoList.Video> list){
        this.list = list;
    }

//    public rankRecyclerViewAdapter(List<VideoList.Video> list) {
//        this.list = list;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemRankBinding binding=RecyclerviewItemRankBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        VideoList.Video video = list.get(position);

        // TODO: 2022/8/11 获取图片，数据获取工具类：List数据转一条String
        holder.binding.imageViewIcon.setImageResource(R.mipmap.nophoto);//默认图片

        if(mContext!=null) {
            try {
                Glide.with(mContext).load(new URL(video.getPoster())).into(holder.binding.imageViewIcon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        holder.binding.textViewName.setText(video.getName());
        //这行报错
        holder.binding.textViewPopularDegree.setText(video.getHot());
        holder.binding.textViewReleaseTime.setText(video.getRelease_date()+" 上映");
        holder.binding.textViewType.setText(video.getTags()+"");
        holder.binding.textViewScore.setText("暂无评分");
        //button点击事件
        holder.binding.buttonGetTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "等我下次再给你看详细的", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewItemRankBinding binding;
        public MyViewHolder(@NonNull RecyclerviewItemRankBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    /**
     * 用于数据更新
     * 先将初始化的时候存储的数据清除，然后将传来的从数据库中读取的数据追加到这个已经清空的列表中
     * 这样适配器实际使用的列表和初始化中的列表都是一样的了，
     * @param datas
     */
    public void setData(List<VideoList.Video> datas) {
        if (!list.isEmpty()) {
            list.clear();
            this.notifyDataSetChanged();
        }
        list.addAll(datas);
        this.notifyDataSetChanged();
    }


}
/*
*
* binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new MyItemDecoration(this));
        // TODO: 2022/8/11 此处记得使用分隔线
        for (int i = 0; i < 10; i++) {
            list.add("xiao long 666");
        }

        recyclerviewAdapter adapter=new recyclerviewAdapter(list);
        binding.recyclerView.setAdapter(adapter);*/

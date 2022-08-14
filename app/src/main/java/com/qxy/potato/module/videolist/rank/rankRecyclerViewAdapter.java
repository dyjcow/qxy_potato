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

import java.net.URI;
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
        RecyclerviewItemRankBinding binding=RecyclerviewItemRankBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        VideoList.Video video = list.get(position);

        // TODO: 2022/8/11 更具传进来的数据动态更改
        holder.binding.imageViewIcon.setImageResource(R.mipmap.nophoto);//默认图片
        Glide.with(mContext).load(video.getPoster()).into(holder.binding.imageViewIcon);

        holder.binding.textViewName.setText(video.getName());
        holder.binding.textViewPopularDegree.setText(video.getHot());
        holder.binding.textViewReleaseTime.setText(video.getRelease_date()+" 上映");
        holder.binding.textViewType.setText(video.getTags().get(0));
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
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewItemRankBinding binding;
        public MyViewHolder(@NonNull RecyclerviewItemRankBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
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

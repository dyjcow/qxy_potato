package com.qxy.potato.module.rank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potato.R;
import com.qxy.potato.databinding.RecyclerviewItemRankBinding;

import java.util.List;

/**
 * @author yinxiaolong
 * @describe :recyclerViewAdapter
 * @data: :2022/8/11
 */
public class rankRecyclerViewAdapter extends RecyclerView.Adapter<rankRecyclerViewAdapter.MyViewHolder> {
    //此处的范型改为需要的bean类
    private List<String> list;

    public rankRecyclerViewAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemRankBinding binding=RecyclerviewItemRankBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // TODO: 2022/8/11 更具传进来的数据动态更改
        holder.binding.imageViewIcon.setImageResource(R.drawable.ic_launcher_background);
        holder.binding.textViewName.setText("月球独行");
        holder.binding.textViewPopularDegree.setText("1256.5万");
        holder.binding.textViewReleaseTime.setText("2020-02-20");
        holder.binding.textViewType.setText("科幻");
        holder.binding.textViewScore.setText("6.8");
        //button点击事件
        holder.binding.buttonGetTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "点击到了", Toast.LENGTH_SHORT).show();
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

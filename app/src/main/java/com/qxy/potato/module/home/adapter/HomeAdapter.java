package com.qxy.potato.module.home.adapter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qxy.potato.R;

import java.util.List;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.adapter
 * @date :2022/8/16 21:29
 */
public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List list ) {
        super(layoutResId,list);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position<3){
            holder.setVisible(R.id.home_textView_Top,true);
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String item) {

        holder.setText(R.id.textView,item);
        //默认没有top标签
        holder.findView(R.id.home_textView_Top).setVisibility(View.GONE);
        Log.d("position", "convert: "+getItemPosition(item));

    }
}

package com.qxy.potatos.module.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dylanc.viewbinding.brvah.BaseViewHolderUtilKt;
import com.qxy.potatos.R;
import com.qxy.potatos.bean.Good;
import com.qxy.potatos.databinding.RvItemSearchBinding;

import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/12/30 10:17
 * @description：adapter
 * @modified By：
 * @version: 1.0
 */
public class SearchAdapter extends BaseQuickAdapter<Good, BaseViewHolder> {


	public SearchAdapter(@Nullable List<Good> data) {
		super(R.layout.rv_item_search, data);
	}

	@Override protected void convert(@NonNull BaseViewHolder baseViewHolder, Good good) {
		RvItemSearchBinding binding = BaseViewHolderUtilKt.getBinding(baseViewHolder,RvItemSearchBinding::bind);

		binding.tvName.setText(good.getGname());
	}
}

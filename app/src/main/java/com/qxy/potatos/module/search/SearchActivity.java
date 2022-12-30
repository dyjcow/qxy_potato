package com.qxy.potatos.module.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.qxy.potatos.base.BaseActivity;
import com.qxy.potatos.bean.Good;
import com.qxy.potatos.databinding.ActivitySearchBinding;
import com.qxy.potatos.util.EmptyViewUtil;
import com.tamsiree.rxkit.view.RxToast;

import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter, ActivitySearchBinding>
		implements ISearchView{

	private SearchAdapter adapter;

	/**
	 * 初始化presenter，也是与Activity的绑定
	 *
	 * @return 返回new的Presenter层的值
	 */
	@Override protected SearchPresenter createPresenter() {
		return new SearchPresenter(this);
	}

	/**
	 * 载入view的一些操作
	 */
	@Override protected void initView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		getBinding().rvItem.setLayoutManager(layoutManager);
		adapter = new SearchAdapter(null);
		getBinding().rvItem.setAdapter(adapter);

		getBinding().btnSearch.setOnClickListener(v -> {
			String editTxt = getBinding().editText.getText().toString().trim();
			if (editTxt.equals("")){
				RxToast.warning("请正确输入");
			}else {
				presenter.searchInfo(editTxt);
			}
		});
	}

	/**
	 * 载入数据操作
	 */
	@Override protected void initData() {

	}

	@Override public void showResult(List<Good> goodList) {
		adapter.setList(goodList);
	}

	@Override public void showEmpty() {
		View emptyView = EmptyViewUtil.getEmptyDataView(getBinding().rvItem);
		adapter.setList(null);
		adapter.setEmptyView(emptyView);
	}

	@Override public void showError() {
		View emptyView = EmptyViewUtil.getEmptyDataView(getBinding().rvItem);
		adapter.setList(null);
		adapter.setEmptyView(emptyView);
		RxToast.error("数据错误");
	}
}
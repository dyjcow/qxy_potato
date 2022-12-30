package com.qxy.potatos.module.search;

import com.qxy.potatos.base.BaseBean;
import com.qxy.potatos.base.BaseObserver;
import com.qxy.potatos.base.BasePresenter;
import com.qxy.potatos.bean.Good;

import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/12/30 8:53
 * @description：搜索P层
 * @modified By：
 * @version: 1.0
 */
public class SearchPresenter extends BasePresenter<ISearchView> {

	private final String uno = "hQwMh1xTpK";
	public SearchPresenter(ISearchView baseView) {
		super(baseView);
	}

	public void searchInfo(String info){
		addDisposable(apiServer.searchGoodInfo(uno,info,1,10),
				new BaseObserver<BaseBean<List<Good>>>(baseView, true) {
					@Override public void onError(String msg) {
						baseView.showError();
					}

					@Override public void onSuccess(BaseBean<List<Good>> o) {
						if (o.empty){
							baseView.showEmpty();
						}else {
							baseView.showResult(o.content);
						}


					}
				});
	}
}

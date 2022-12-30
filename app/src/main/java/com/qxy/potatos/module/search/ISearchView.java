package com.qxy.potatos.module.search;

import com.qxy.potatos.base.BaseView;
import com.qxy.potatos.bean.Good;

import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/12/30 8:37
 * @description：信息查询
 * @modified By：
 * @version: 1.0
 */
public interface ISearchView extends BaseView {

	void showResult(List<Good> goodList);

	void showEmpty();

	void showError();
}

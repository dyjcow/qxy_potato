package com.qxy.potato.base;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:26
 * @description：接口base类
 * @modified By：
 * @version: 1.0
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 操作成功隐藏dialog和显示成功
     */
    void SuccessHideLoading();

    /**
     * 操作失败隐藏dialog和显示失败
     */
    void FailedHideLoading();

    /**
     * 错误
     *
     * @param bean 错误信息
     */
    @SuppressWarnings("rawtypes")
    void onErrorCode(BaseBean bean);
}

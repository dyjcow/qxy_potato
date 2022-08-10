package com.qxy.potato.base;

import com.qxy.potato.http.API;
import com.qxy.potato.http.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:29
 * @description：presenter的base层
 * @modified By：
 * @version: 1.0
 */
public class BasePresenter<V extends BaseView> {

    private CompositeDisposable compositeDisposable;
    public V baseView;

    /**
     * 这个后面可以直接用   Example：apiServer.login(username, password)；
     */
    protected API.SZApi apiServer = RetrofitService.getInstance().getApiService();

    public BasePresenter(V baseView) {
        this.baseView = baseView;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 返回 view
     */
    public V getBaseView() {
        return baseView;
    }

    @SuppressWarnings("all")
    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable
                .add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}

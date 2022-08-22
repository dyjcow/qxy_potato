package com.qxy.potato.base;

import com.qxy.potato.R;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:33
 * @description：BaseObserver
 * @modified By：
 * @version: 1.0
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {

    protected BaseView view;
    private boolean isShowDialog;

    protected BaseObserver(BaseView view) {
        this.view = view;
    }

    /**
     * 带进度条的初始化方法
     *
     * @param view         view
     * @param isShowDialog 是否显示进度条
     */
    protected BaseObserver(BaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        if (view != null && isShowDialog) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.FailedHideLoading();
        }
        BaseException be;

        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
                //回调到view层 处理 或者根据项目情况处理
                if (view != null) {
                    view.onErrorCode(new BaseBean<>(be.getErrorCode(), be.getErrorMsg()));
                } else {
                    onError(be.getErrorMsg());
                }
                //系统异常
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(MyUtil.getString(R.string.BAD_NETWORK_MSG), e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(MyUtil.getString(R.string.CONNECT_ERROR_MSG), e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(MyUtil.getString(R.string.CONNECT_TIMEOUT_MSG), e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(MyUtil.getString(R.string.PARSE_ERROR_MSG), e);
                } else {
                    be = new BaseException(MyUtil.getString(R.string.OTHER_MSG), e);
                }
            }
        } else {
            be = new BaseException(MyUtil.getString(R.string.OTHER_MSG));
        }
        onError(be.getErrorMsg());
    }

    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.SuccessHideLoading();
        }
    }


    public abstract void onSuccess(T o);

    public abstract void onError(String msg);

}
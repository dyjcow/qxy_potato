package com.qxy.potatos.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.qxy.potatos.util.ActivityUtil;
import com.qxy.potatos.annotation.BindEventBus;
import com.qxy.potatos.util.MyUtil;
import com.dylanc.viewbinding.base.ViewBindingUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:36
 * @description：Fragment的base类
 * @modified By：
 * @version: 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseFragment<P extends BasePresenter, VB extends ViewBinding> extends Fragment implements BaseView {

    protected Context mContext;

    protected P presenter;

    private VB binding;


    /**
     * 显示加载中
     */
    @Override
    public void showLoading() {
        MyUtil.showLoading(mContext);
    }

    /**
     * 操作成功隐藏dialog和显示成功
     */
    @Override
    public void SuccessHideLoading() {
        MyUtil.dismissSuccessLoading();
    }

    /**
     * 操作失败隐藏dialog和显示失败
     */
    @Override
    public void FailedHideLoading() {
        MyUtil.dismissFailedLoading();
    }

    @Override
    public void onErrorCode(BaseBean bean) {
    }

    /**
     * 在这里要返回view的根路径
     *
     * @return 返回绑定的view
     */
    public VB getBinding() {
        return binding;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false);
        //得到context,在后面的子类Fragment中都可以直接调用
        mContext = ActivityUtil.getCurrentActivity();
        presenter = createPresenter();
        initView();
        initData();
        return binding.getRoot();
    }

    /**
     * 创建 presenter
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
        //销毁时，解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
        binding = null;
    }

    private void initListener() {
    }


}

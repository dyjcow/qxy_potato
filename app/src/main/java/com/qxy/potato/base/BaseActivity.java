package com.qxy.potato.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


import com.dylanc.viewbinding.base.ViewBindingUtil;
import com.qxy.potato.R;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.util.DisplayUtil;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.NetworkUtil;
import com.qxy.potato.util.ToastUtil;
import com.tamsiree.rxkit.view.RxToast;
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import org.greenrobot.eventbus.EventBus;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 9:45
 * @description：Activity的base类
 * @modified By：
 * @version: 1.0
 */
public abstract class BaseActivity<P extends BasePresenter<? extends BaseView>,VB extends ViewBinding> extends AppCompatActivity implements BaseView{

    private VB binding;
    /**
     * presenter层的引用
     */
    protected P presenter;

    /**
     * 错误
     *
     * @param bean 错误信息
     */
    @Override
    public void onErrorCode(BaseBean bean) {
        ToastUtil.showToast(bean.msg);
    }

    /**
     * 初始化presenter，也是与Activity的绑定
     *
     * @return 返回new的Presenter层的值
     */
    protected abstract P createPresenter();


    /**
     * 载入view的一些操作
     */
    protected abstract void initView();


    /**
     * 载入数据操作
     */
    protected abstract void initData();


    /**
     * {@inheritDoc}
     * <p>
     * Perform initialization of all fragments.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getClass().isAnnotationPresent(BindEventBus.class)){
            EventBus.getDefault().register(this);
        }
        DisplayUtil.setCustomDensity(this);
        UltimateBarX.statusBarOnly(this)
                .light(true)
                .transparent()
                .apply();
        //强制使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = createPresenter();

        initView();
        initData();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)
                || NetworkUtil.getNetworkType(this) == 0){
            RxToast.warning(MyUtil.getString(R.string.disconnected_network));
        }else if (NetworkUtil.getNetworkType(this) != 1){
            RxToast.info(MyUtil.getString(R.string.note_use));
        }
    }

    /**
     * 解除presenter与Activity的绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.getClass().isAnnotationPresent(BindEventBus.class)){
            EventBus.getDefault().unregister(this);
        }
        if (presenter != null){
            presenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        MyUtil.showLoading(this);
    }

    @Override
    public void SuccessHideLoading() {
        MyUtil.dismissSuccessLoading();
    }

    @Override
    public void FailedHideLoading() {
        MyUtil.dismissFailedLoading();
    }

    /**
     * 查看当前是否为深色模式
     *
     * @param context 传入当前context
     * @return 返回ture 偶然false
     */
    public Boolean getDarkModeStatus(Context context){
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    public VB getBinding() {
        return binding;
    }
}
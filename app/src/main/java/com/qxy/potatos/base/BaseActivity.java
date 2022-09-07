package com.qxy.potatos.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


import com.dylanc.viewbinding.base.ViewBindingUtil;
import com.qxy.potatos.annotation.BindEventBus;
import com.qxy.potatos.annotation.InitAIHand;
import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier;
import com.qxy.potatos.util.AI.tracker.MotionEventTracker;
import com.qxy.potatos.util.DisplayUtil;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.MyUtil;
import com.qxy.potatos.util.QueueUtil;
import com.qxy.potatos.util.ToastUtil;
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 9:45
 * @description：Activity的base类
 * @modified By：
 * @version: 1.0
 */
public abstract class BaseActivity<P extends BasePresenter<? extends BaseView>, VB extends ViewBinding>
        extends AppCompatActivity implements BaseView, MotionEventTracker.ITrackDataReadyListener {

    /**
     * presenter层的引用
     */
    protected P presenter;

    private VB binding;

    private OperatingHandClassifier classifier;

    private MotionEventTracker tracker;

    public int hand = 1;



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
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().register(this);
        }
        if (this.getClass().isAnnotationPresent(InitAIHand.class)){
            classifier = new OperatingHandClassifier(this);
            classifier.checkAndInit();
            tracker = new MotionEventTracker(this);
            tracker.checkAndInit(this);
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
     * 解除presenter与Activity的绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBus.getDefault().unregister(this);
        }
        if (this.getClass().isAnnotationPresent(InitAIHand.class)){
            classifier.close();
        }
        if (presenter != null) {
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
     * 错误
     *
     * @param bean 错误信息
     */
    @Override
    public void onErrorCode(BaseBean bean) {
        ToastUtil.showToast(bean.msg);
    }

    public VB getBinding() {
        return binding;
    }

    /**
     * Called to process touch screen events.  You can override this to
     * intercept all touch screen events before they are dispatched to the
     * window.  Be sure to call this implementation for touch screen events
     * that should be handled normally.
     *
     * @param ev The touch screen event.
     * @return boolean Return true if this event was consumed.
     */
    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (tracker != null && ev != null){
            tracker.recordMotionEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override public void onTrackDataReady(@NonNull JSONArray dataList) {
        if (classifier != null){
            classifier.classifyAsync(dataList).addOnSuccessListener(result -> {
                hand = QueueUtil.getRecentHand(result.getLabelInt());
                LogUtil.d(MotionEventTracker.TAG,result.getLabel());
            }).addOnFailureListener(e -> LogUtil.e(MotionEventTracker.TAG,e.toString()));
        }
    }

}
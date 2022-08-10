package com.qxy.potato;

import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.base.BaseView;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.util.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@BindEventBus
public class MainActivity extends BaseActivity{

    /**
     * 初始化presenter，也是与Activity的绑定
     *
     * @return 返回new的Presenter层的值
     */
    @Override
    protected BasePresenter<? extends BaseView> createPresenter() {
        return null;
    }

    /**
     * 载入view的一些操作
     */
    @Override
    protected void initView() {
        PictureGirl girl = new PictureGirl();
        BaseEvent<PictureGirl> ev = new BaseEvent<>(1,girl);
        EventBusUtil.sendEvent(ev);
    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(BaseEvent event){

    }
}
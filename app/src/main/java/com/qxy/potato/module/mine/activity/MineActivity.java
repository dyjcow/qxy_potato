package com.qxy.potato.module.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.databinding.ActivityMainBinding;
import com.qxy.potato.module.mine.presenter.MinePresenter;
import com.qxy.potato.module.mine.view.IMineView;
import com.qxy.potato.module.mine.workmanager.ClientCancelWork;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.EventBusUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BindEventBus
public class MineActivity extends BaseActivity<MinePresenter, ActivityMainBinding> implements IMineView, IApiEventHandler {
    DouYinOpenApi douYinOpenApi;
    MMKV kv = MMKV.defaultMMKV();

    /**
     * 初始化presenter，也是与Activity的绑定
     *
     * @return 返回new的Presenter层的值
     */
    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    /**
     * 载入view的一些操作
     */
    @Override
    protected void initView() {
        if (kv.decodeBool(GlobalConstant.IS_LOGIN,false)){
            getBinding().btnRegister.setVisibility(View.GONE);
        }

        presenter.loadImg();
        PictureGirl girl = new PictureGirl();
        BaseEvent<PictureGirl> ev = new BaseEvent<>(1,girl);
        EventBusUtil.sendEvent(ev);
        getBinding().btnRegister.setOnClickListener(v -> ActivityUtil.startActivity(LoginActivity.class));

    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
        initClient();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(BaseEvent event){

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) baseResp;
            if (baseResp.isSuccess()) {
                LogUtil.d("onRES");
                presenter.getAccessToken(response.authCode);
            }else {
                ToastUtil.showToast("授权失败");
            }
        }
    }

    @Override
    public void onErrorIntent(Intent intent) {
        if (!kv.decodeBool(GlobalConstant.IS_LOGIN,false)) ToastUtil.showToast("Intent出错");
    }

    /**
     * 成功登录的操作
     */
    @Override
    public void loginSuccess() {
        getBinding().btnRegister.setVisibility(View.GONE);
        ToastUtil.showToast("授权登录成功");
    }

    /**
     * 载入照片
     *
     * @param url
     */
    @Override
    public void loadImg(String url) {
        Glide.with(this)
                .load(url)
                .into(getBinding().imgBackgroud);
    }

    /**
     * 登录失败的操作
     */
    @Override
    public void loginFailed(String msg) {
        ToastUtil.showToast(msg);
    }

    /**
     * 启动2h后告知app连接失效的程序
     */
    @Override
    public void cancelClientValue(){
        WorkRequest request = new OneTimeWorkRequest.Builder(ClientCancelWork.class)
                .setInitialDelay(2, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(this).enqueue(request);
    }

    /**
     * 初始化连接型token
     */
    private void initClient() {
        kv.encode(GlobalConstant.IS_CLIENT,false);
        presenter.getClientToken();
    }
}
package com.qxy.potato.module.mine.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.databinding.ActivityLoginBinding;
import com.qxy.potato.module.home.activity.HomeActivity;
import com.qxy.potato.module.mine.presenter.LoginPresenter;
import com.qxy.potato.module.mine.view.ILoginView;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.ToastUtil;
import com.tamsiree.rxkit.RxTool;
import com.tamsiree.rxkit.view.RxToast;

public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements ILoginView, IApiEventHandler {

    DouYinOpenApi douYinOpenApi;
    private long mExitTime = 0;
    private boolean is_clicked_user_agreement = false;

    /**
     * 初始化presenter，也是与Activity的绑定
     *
     * @return 返回new的Presenter层的值
     */
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    /**
     * 载入view的一些操作
     */
    @Override
    protected void initView() {
        getBinding().btnLogin.setOnClickListener(v -> {
            if (is_clicked_user_agreement) {
                sendAuth();
            } else {
                showAnimator();
            }
        });
        getBinding().checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_clicked_user_agreement = b;
            }
        });
        getBinding().tvUserAgreement.setOnClickListener(view -> ToastUtil.showToast("待添加用户协议"));
    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
    }

    @Override
    public boolean sendAuth() {
        Authorization.Request request = new Authorization.Request();
        request.scope = "user_info,trial.whitelist";                          // 用户授权时必选权限
        // 用户授权时可选权限（默认选择）
        request.optionalScope1 = "fans.list," +
                "following.list," +
                "video.list," +
                "video.data";
//        request.optionalScope0 = mOptionalScope1;    // 用户授权时可选权限（默认不选）
        request.state = "ww";                                   // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.qxy.potato.module.mine.activity.LoginActivity";
        return douYinOpenApi.authorize(request);               // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权

    }

    private void showAnimator() {
        RxToast.info(MyUtil.getString(R.string.read_and_click), 500);
        ObjectAnimator animatorLeft = ObjectAnimator.ofFloat(getBinding().checkBox,
                "translationX", -20f, 0f, 20f, 0f);
        ObjectAnimator animatorRight = ObjectAnimator.ofFloat(getBinding().tvUserAgreement,
                "translationX", -20f, 0f, 20f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(animatorLeft).with(animatorRight);
        animatorSet.start();
    }

    /**
     * 成功登录的操作
     */
    @Override
    public void loginSuccess() {
        ActivityUtil.startActivity(HomeActivity.class, true);
    }

    /**
     * 登录失败的操作
     *
     * @param msg
     */
    @Override
    public void loginFailed(String msg) {
        ToastUtil.showToast(msg);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {
        int OVER_TIME = 2000;
        if ((System.currentTimeMillis() - mExitTime) > OVER_TIME) {
            ToastUtil.showToast(getResources().getString(R.string.double_quit) + getResources().getString(R.string.app_name));
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            ActivityUtil.closeAllActivity();
        }
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
            } else {
                ToastUtil.showToast("授权失败");
            }
        }
    }

    @Override
    public void onErrorIntent(Intent intent) {

    }
}
package com.qxy.potato.module.mine.activity;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.databinding.ActivityLoginBinding;
import com.qxy.potato.module.mine.presenter.LoginPresenter;
import com.qxy.potato.module.mine.view.ILoginView;

public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements ILoginView {
    DouYinOpenApi douYinOpenApi;

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
        getBinding().btnLogin.setOnClickListener(v -> sendAuth());
    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {
        douYinOpenApi = DouYinOpenApiFactory.create(this);
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
        request.callerLocalEntry = "com.qxy.potato.module.mine.activity.MineActivity";
        return douYinOpenApi.authorize(request);               // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权

    }


}
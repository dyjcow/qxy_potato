package com.qxy.potato.module.mine.activity;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewFragment;

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
import com.qxy.potato.R;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.bean.VideoVersion;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.databinding.ActivityMainBinding;
import com.qxy.potato.module.mine.fragment.TestFragment;
import com.qxy.potato.module.mine.fragment.VideoDisplayragment;
import com.qxy.potato.module.mine.presenter.MinePresenter;
import com.qxy.potato.module.mine.view.IMineView;
import com.qxy.potato.module.mine.workmanager.ClientCancelWork;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;
import com.tencent.mmkv.MMKV;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

@BindEventBus
public class MineActivity extends BaseActivity<MinePresenter, ActivityMainBinding> implements IMineView, IApiEventHandler {
    DouYinOpenApi douYinOpenApi;
    MMKV kv = MMKV.defaultMMKV();

    private long mBackPressed;//用来记录按下时间，连点两次返回键才返回
    private static final int BACK_PRESSED_INTERVAL = 1500;//时间间隔

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
            LogUtil.d(kv.decodeString(GlobalConstant.ACCESS_TOKEN));
        }

        presenter.loadImg();
        getBinding().btnRegister.setOnClickListener(v -> ActivityUtil.startActivity(LoginActivity.class));

        //跳转到榜单
//        getBinding().imgRank.setOnClickListener(v->ActivityUtil.startActivity(RankActivity.class));

        //跳转到Webview测试用
//        getBinding().imgRank.setOnClickListener(v->ActivityUtil.startActivity(WebViewTestActivity.class));


        //TODO： 个人界面碎片按下面样例添加；跳转到视频碎片需传入url。
//        //个人界面碎片
//        getFragmentManager().beginTransaction().add(R.id.fragment_webview, new TestFragment()).commit();
//        //打开视频详情页（添加tag）
//        getFragmentManager().beginTransaction().add(R.id.fragment_webview, VideoDisplayragment.newInstance(url),"WebView").addToBackStack(null).commit();


        presenter.getClientVersionDemo();

    }

    /**
     * 载入数据操作
     */
    @Override
    protected void initData() {
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
        initClient();
        mBackPressed =System.currentTimeMillis();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(BaseEvent<VideoVersion.Version> event){
        if (event.getEventCode() == EventCode.SELECT_VERSION){
            ToastUtil.showToast(String.valueOf(event.getData().getVersion()));
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


    /**
     * 配置Webview的返回键,碎片切换要加到返回栈中
     */
    @Override public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentByTag("WebView");
        if(fragment!=null){//说明现在是webview播放页面
            VideoDisplayragment videoDisplayragment = (VideoDisplayragment) fragment;
            WebView webView = videoDisplayragment.getmWebViewWebView();
            //不空且是焦点
            if (webView != null && webView.isFocused()) {

                if (webView.canGoBack()) {//WebView的向前
                    webView.goBack();
                } else {
                    if(mBackPressed + BACK_PRESSED_INTERVAL > System.currentTimeMillis()){//连续两次才返回到个人碎片（1.5s内）
                        super.onBackPressed();
                        return;
                    }else{//提示
                        ToastUtil.showToast("再点一次返回键返回个人界面");
                        mBackPressed = System.currentTimeMillis();

                    }
                }
            }
        } else {//正常系统返回键
                super.onBackPressed();
            }


    }


}
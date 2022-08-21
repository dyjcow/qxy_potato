package com.qxy.potato.module.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.qxy.potato.R;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.bean.UserInfo;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.databinding.ActivityHomeBinding;
import com.qxy.potato.module.Follow.activity.FollowActivity;
import com.qxy.potato.module.home.adapter.HomeAdapter;
import com.qxy.potato.module.home.adapter.HomeItemDecoration;
import com.qxy.potato.module.home.presenter.HomePresenter;
import com.qxy.potato.module.home.view.IHomeView;
import com.qxy.potato.module.mine.activity.LoginActivity;
import com.qxy.potato.module.videorank.activity.RankActivity;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.DisplayUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.tamsiree.rxui.view.dialog.RxDialogSure;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BindEventBus
public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding> implements IHomeView, IApiEventHandler {

    private MMKV mmkv = MMKV.defaultMMKV();
    private HomeAdapter adapter;
    private Integer getLIke = 0;
    private DouYinOpenApi douYinOpenApi;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    List<MyVideo.Videos> list = new ArrayList<>();

    @Override
    protected void initView() {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.home_collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        //折叠框的配置
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#003547"));

        //根据滑动监听来判断是否显示title
        //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {


            if (getSupportActionBar().getHeight() + DisplayUtil.dp2px(20) - appBarLayout1.getHeight() == verticalOffset) {
                //折叠监听

                collapsingToolbarLayout.setTitle(" 作品");
                getBinding().homeIconSmall.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(),"折叠了",Toast.LENGTH_SHORT).show();
            } else {   //展开监听
                // Toast.makeText(getApplicationContext(),"展开了",Toast.LENGTH_SHORT).show();
                getBinding().homeIconSmall.setVisibility(View.INVISIBLE);
                collapsingToolbarLayout.setTitle(" ");
            }
        });
        //点击小头像返回顶部
        getBinding().homeIconSmall.setOnClickListener(view -> getBinding().nestedScrollViewLayout.smoothScrollTo(0, 0, 200));
        //跳转到关注页
        // TODO: 2022/8/20 跳转到关注页 
        getBinding().homeTextViewFollower.setOnClickListener(view -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("type", "Followings");
                    ActivityUtil.startActivity(FollowActivity.class, map);
                }
        );
        //跳转到粉丝页
        // TODO: 2022/8/20 跳转到粉丝页 
        getBinding().homeTextViewFans.setOnClickListener(view -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", "Fans");
            ActivityUtil.startActivity(FollowActivity.class, map);
        });
        //点赞的dialog
        getBinding().homeTextViewLike.setOnClickListener(view -> {
            RxDialogSure rxDialogSure = new RxDialogSure(this);
            rxDialogSure.setLogo(R.drawable.potato);
            rxDialogSure.setSureListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rxDialogSure.cancel();
                }
            });
            rxDialogSure.setContent("你一共获得" + getLIke + "点赞");
            rxDialogSure.show();
        });
        //通过DrawerLayout打开榜单页面 和登录页
        getBinding().homeNavigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_rank) {
                ActivityUtil.startActivity(RankActivity.class);
            } else if (item.getItemId() == R.id.nav_login) {
                if (!mmkv.decodeBool(GlobalConstant.IS_LOGIN)) {
                    ActivityUtil.startActivity(LoginActivity.class, true);
                } else {
                    mmkv.encode(GlobalConstant.IS_LOGIN, false);
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            return true;
        });

        //跳转去登录页
        getBinding().homeIcon.setOnClickListener(v -> {
            if (!mmkv.decodeBool(GlobalConstant.IS_LOGIN))
                getBinding().homeNavigationView.getMenu().getItem(1).setTitle("登出");
            ActivityUtil.startActivity(LoginActivity.class, true);
        });

        //下拉加载更多
        getBinding().homeRefreshlayout.setEnableRefresh(false);
        getBinding().homeRefreshlayout.setEnableLoadMore(true);
        getBinding().homeRefreshlayout.setRefreshFooter(new ClassicsFooter(this));
        getBinding().homeRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (isHasMore && checkList.size() != 0) {
                    presenter.getPersonalVideoList(cursor);
                    refreshLayout.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                    refreshLayout.setEnableLoadMore(false);
                    getBinding().homeRecyclerviewFooter.setVisibility(View.VISIBLE);
                    ;
                }
            }
        });

        //首次进入获取clientToken
        initClient();
        //设置登录回调
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
    }


    /**
     * 加载Toolbar的menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        return true;
    }

    /**
     * 打开drawerLayout
     *
     * @param item
     * @return
     */
    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.open_rank) {
            getBinding().drawerLayout.openDrawer(Gravity.RIGHT);
        }
        return true;
    }

    private long cursor = 0;
    private boolean isHasMore = false;

    /**
     * 相关数据的初始化
     */
    @Override
    protected void initData() {
        presenter.getPersonInfo();
        presenter.getPersonalVideoList(cursor);
        Glide.with(this).load("https://www.lxtlovely.top/getpic.php").into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                getBinding().homeBackground.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        boolean isLogin = mmkv.decodeBool(GlobalConstant.IS_LOGIN);
        if (isLogin) {
            getBinding().homeNavigationView.getMenu().getItem(1).setTitle("登出");
        } else {
            getBinding().homeNavigationView.getMenu().getItem(1).setTitle("登录");
        }
        LogUtil.d("initData");
    }


    @Override
    public void showPersonalInfo(UserInfo userInfo) {
        getBinding().homeTextViewLike.setText(getLIke + "获赞");
        getBinding().homeTextViewFans.setText(mmkv.decodeInt(GlobalConstant.FANS_TOTAL,0) + "粉丝");
        getBinding().homeTextViewFollower.setText(mmkv.decodeInt(GlobalConstant.FOLLOWINGS_TOTAL,0) + "关注");
        getBinding().textViewIntroduce.setText("无");
        getBinding().homeTextviewSchool.setText("无");
        getBinding().homeTextviewPlace.setText((userInfo.getCountry() + userInfo.getDistrict()).equals("") ? "无" : (userInfo.getCountry() + userInfo.getDistrict()));
        getBinding().homeTextviewAge.setText(userInfo.getGender() + "");
        Glide.with(this).load(userInfo.getAvatar()).into(getBinding().homeIconSmall);
        Glide.with(this).load(userInfo.getAvatar_larger()).into(getBinding().homeIcon);
        getBinding().textViwPersonalName.setText(userInfo.getNickname());


    }

    private List<MyVideo.Videos> checkList = new ArrayList<>();

    @Override
    public void showPersonalVideo(List<MyVideo.Videos> videos, boolean isHasMore, long cursor) {
        this.isHasMore = isHasMore;
        this.cursor = cursor;
        adapter = new HomeAdapter(R.layout.reycylerview_item_home, list);
        adapter.addChildClickViewIds(R.id.home_item_imageView);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);

        //recyclerview初始化
        getBinding().recyclerViewHome.addItemDecoration(new HomeItemDecoration(120, 5, 5, 5));
        getBinding().recyclerViewHome.setLayoutManager(new GridLayoutManager(this, 3));
        getBinding().recyclerViewHome.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                // TODO: 2022/8/20  填入webViewActivity
                /*Intent intent=new Intent(HomeActivity.class,*//*webView*//*);
                intent.putExtra("shareUrl",list.get(position).getShare_url());
                startActivity(intent);*/
            }
        });
        if (videos != null) {

            for (int i = 0; i < videos.size(); i++) {
                checkList.clear();
                if (!videos.get(i).getShare_url().equals("")) {
                    checkList.add(videos.get(i));
                    adapter.addData(videos.get(i));
                }
            }

//            adapter.notifyDataSetChanged();
        }


    }

    /**
     * 成功登录的操作
     */
    @Override
    public void loginSuccess() {
        initData();

        ToastUtil.showToast("授权登录成功");
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
     * 启动对应的后台任务
     *
     * @param duration    间隔时间
     * @param timeUnit    时间计算单位
     * @param tag         事件标签
     * @param workerClass 对应的 Work类
     */
    @Override
    public void startWork(long duration, @NonNull TimeUnit timeUnit, String tag,
                          @NonNull Class<? extends ListenableWorker> workerClass) {
        WorkRequest request = new OneTimeWorkRequest.Builder(workerClass)
                .setInitialDelay(duration, timeUnit)
                .addTag(tag)
                .build();
        WorkManager.getInstance(this).enqueue(request);
    }

    /**
     * 订阅的事件，当请求重新刷新clientToken的时候执行
     *
     * @param event 接收的event事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshClientToken(BaseEvent<String> event) {
        if (event.getEventCode() == EventCode.CLIENT_AGAIN)
            presenter.getClientToken();
    }


    /**
     * 解除presenter与Activity的绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance(this).cancelAllWorkByTag(GlobalConstant.CLIENT_TOKEN);
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


    /**
     * 初始化连接型token
     */
    private void initClient() {
        mmkv.encode(GlobalConstant.IS_CLIENT, false);
        presenter.getClientToken();
    }
}
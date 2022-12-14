package com.qxy.potatos.module.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.qxy.potatos.R;
import com.qxy.potatos.annotation.BindEventBus;
import com.qxy.potatos.annotation.InitAIHand;
import com.qxy.potatos.base.BaseActivity;
import com.qxy.potatos.base.BaseEvent;
import com.qxy.potatos.bean.MyVideo;
import com.qxy.potatos.bean.UserInfo;
import com.qxy.potatos.common.EventCode;
import com.qxy.potatos.common.GlobalConstant;
import com.qxy.potatos.databinding.ActivityHomeBinding;
import com.qxy.potatos.module.Follow.activity.FollowActivity;
import com.qxy.potatos.module.home.adapter.HomeAdapter;
import com.qxy.potatos.module.home.adapter.HomeItemDecoration;
import com.qxy.potatos.module.home.myView.DialogSureCancel;
import com.qxy.potatos.module.home.presenter.HomePresenter;
import com.qxy.potatos.module.home.view.IHomeView;
import com.qxy.potatos.module.mine.activity.LoginActivity;
import com.qxy.potatos.module.mine.activity.WebViewActivity;
import com.qxy.potatos.module.mine.service.PreLoadService;
import com.qxy.potatos.module.videorank.activity.RankActivity;
import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier;
import com.qxy.potatos.util.ActivityUtil;
import com.qxy.potatos.util.DisplayUtil;

import com.qxy.potatos.util.EmptyViewUtil;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.ToastUtil;
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

@InitAIHand
@BindEventBus
public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding> implements IHomeView {


    private final MMKV mmkv = MMKV.defaultMMKV();
    List<MyVideo.Videos> list = new ArrayList<>();
    private HomeAdapter adapter;
    /**
     * ?????????????????????????????????
     */
    private long mExitTime = 0;
    private int like = mmkv.decodeInt(GlobalConstant.LIKE_TOTAL, 0);
    private long cursor = 0;
    private boolean isHasMore = false;
    private List<MyVideo.Videos> checkList = new ArrayList<>();
    private int getLiked = 0;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.home_collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        //??????toolbar
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        //??????????????????
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#003547"));

        //???????????????????????????????????????title
        //verticalOffset?????????appbarLayout?????????????????????appbarlayout??????????????????????????????????????????
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {


            if (getSupportActionBar().getHeight() + DisplayUtil.dp2px(20) - appBarLayout1.getHeight() == verticalOffset) {
                //????????????

                collapsingToolbarLayout.setTitle(getString(R.string.works));
                getBinding().homeIconSmall.setVisibility(View.VISIBLE);


                if (getBinding().homeToolbar.getMenu().findItem(R.id.open_rank) != null) {
                    getBinding().homeToolbar.getMenu().findItem(R.id.open_rank).setIcon(R.mipmap.home_openrank);
                }

            } else {   //????????????
                getBinding().homeIconSmall.setVisibility(View.INVISIBLE);
                collapsingToolbarLayout.setTitle(" ");

                if (getBinding().homeToolbar.getMenu().findItem(R.id.open_rank) != null) {
                    getBinding().homeToolbar.getMenu().findItem(R.id.open_rank).setIcon(R.mipmap.home_openrank2);
                }

            }
        });
        //???????????????????????????
        getBinding().homeIconSmall.setOnClickListener(view -> getBinding().nestedScrollViewLayout.smoothScrollTo(0, 0, 200));
        //??????????????????
        getBinding().homeTextViewFollower.setOnClickListener(view -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("type", "Followings");
                    ActivityUtil.startActivity(FollowActivity.class, map);
                }
        );
        //??????????????????
        getBinding().homeTextViewFans.setOnClickListener(view -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", "Fans");
            ActivityUtil.startActivity(FollowActivity.class, map);
        });
        //?????????dialog
        getBinding().homeTextViewLike.setOnClickListener(view -> {
            RxDialogSure rxDialogSure = new RxDialogSure(this);
            rxDialogSure.setLogo(R.drawable.potato);
            rxDialogSure.setSureListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rxDialogSure.cancel();
                }
            });
            rxDialogSure.setContent(getResources().getString(R.string.total_get)+like+getString(R.string.likes));
            rxDialogSure.show();
        });
        //??????DrawerLayout?????????????????? ????????????
        getBinding().homeNavigationView.setNavigationItemSelectedListener(item -> {
            getBinding().drawerLayout.closeDrawers();
            if (item.getItemId() == R.id.nav_rank) {
                ActivityUtil.startActivity(RankActivity.class);
            } else if (item.getItemId() == R.id.nav_login) {
                eventLogin();
            }
            return true;
        });

        ViewGroup.LayoutParams params = getBinding().homeNavigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2; //?????????????????????


        //??????????????????
        getBinding().homeIcon.setOnClickListener(v -> {
            if (!mmkv.decodeBool(GlobalConstant.IS_LOGIN)) {
                ActivityUtil.startActivity(LoginActivity.class, true);
            }
        });

        //??????????????????
        getBinding().homeRefreshlayout.setEnableRefresh(false);
        getBinding().homeRefreshlayout.setEnableLoadMore(true);
        getBinding().homeRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (isHasMore && checkList.size() != 0) {
                    presenter.getPersonalVideoList(cursor);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });

        //??????????????????clientToken
        initClient();
    }

    private void eventLogin() {
        if (!mmkv.decodeBool(GlobalConstant.IS_LOGIN)) {
            ActivityUtil.startActivity(LoginActivity.class, true);
        } else {
            DialogSureCancel sureCancel = new DialogSureCancel(this, hand);
            sureCancel.setContent(getString(R.string.sure_to_login_out));
            sureCancel.setSure(getString(R.string.sure));
            sureCancel.setCancel(getString(R.string.cancel));
            sureCancel.setSureListener(v -> {
                mmkv.encode(GlobalConstant.IS_LOGIN, false);
                ActivityUtil.startActivity(HomeActivity.class, true);
            });
            sureCancel.setCancelListener(v -> {
                sureCancel.cancel();
                getBinding().drawerLayout.closeDrawers();
            });
            sureCancel.show();
        }
    }

    /**
     * ??????????????????token
     */
    private void initClient() {
        mmkv.encode(GlobalConstant.IS_CLIENT, false);
        presenter.getClientToken();
    }

    /**
     * ????????????????????????
     */
    @Override
    protected void initData() {
        presenter.getPersonInfo();
        presenter.getPersonalVideoList(cursor);
        Glide.with(this).load(getString(R.string.bg_url)).into(new CustomTarget<Drawable>() {
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
            getBinding().homeNavigationView.getMenu().getItem(2).setTitle(getString(R.string.login_out));
            startHideService();
        } else {
            getBinding().homeNavigationView.getMenu().getItem(2).setTitle(getString(R.string.login_home));
        }
        LogUtil.d("initData");
    }

    /**
     * ??????presenter???Activity?????????
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopHideService();
        WorkManager.getInstance(this).cancelAllWorkByTag(GlobalConstant.CLIENT_TOKEN);
    }

    @Override
    public void showPersonalInfo(UserInfo userInfo) {

        getBinding().homeTextViewLike.setText(like + getString(R.string.likes));
        getBinding().homeTextViewFans.setText(mmkv.decodeInt(GlobalConstant.FANS_TOTAL,0) + getString(R.string.fans));
        getBinding().homeTextViewFollower.setText(mmkv.decodeInt(GlobalConstant.FOLLOWINGS_TOTAL,0) + getString(R.string.followings));
        getBinding().textViewIntroduce.setText(getString(R.string.home_introduce));
        getBinding().homeTextviewSchool.setText(getString(R.string.GDUT));
        getBinding().homeTextviewPlace.setText((userInfo.getCountry() + userInfo.getDistrict()).equals("")
                ? getString(R.string.China) : (userInfo.getCountry() + userInfo.getDistrict()));
        String gender;

        if (userInfo.getGender() == 0 ||userInfo.getGender() == 1){
            gender = getString(R.string.man);
        }else {
            gender = getString(R.string.woman);
        }
        getBinding().homeTextviewAge.setText(gender);
        Glide.with(this).load(userInfo.getAvatar()).into(getBinding().homeIconSmall);
        Glide.with(this).load(userInfo.getAvatar_larger()).into(getBinding().homeIcon);
        getBinding().textViwPersonalName.setText(userInfo.getNickname());
    }

    @Override
    public void showPersonalVideo(List<MyVideo.Videos> videos, boolean isHasMore, long cursor) {
        this.isHasMore = isHasMore;
        this.cursor = cursor;
        if (adapter == null){
            adapter = new HomeAdapter(R.layout.reycylerview_item_home, list);
            adapter.addChildClickViewIds(R.id.home_item_imageView);
            adapter.setAnimationEnable(true);
            adapter.setAnimationFirstOnly(true);
            adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);

            //recyclerview?????????
            getBinding().recyclerViewHome.addItemDecoration(new HomeItemDecoration(120, 5, 5, 5));
            getBinding().recyclerViewHome.setLayoutManager(new GridLayoutManager(this, 3));
            getBinding().recyclerViewHome.setAdapter(adapter);
        }

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putStringArrayListExtra("url",getURLList());
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        if (videos != null) {
            for (int i = 0; i < videos.size(); i++) {
                checkList.clear();
                if (!videos.get(i).getShare_url().equals("")) {
                    getLiked += videos.get(i).getStatistics().getDigg_count();
                    checkList.add(videos.get(i));
                    adapter.addData(videos.get(i));
                }
            }
            if (like <= getLiked) {
                mmkv.encode(GlobalConstant.LIKE_TOTAL, getLiked);
                like = getLiked;
            }
            getBinding().homeTextViewLike.setText(like+getString(R.string.likes));

        }
        getBinding().homeRefreshlayout.finishLoadMore(true);

    }

    private void startHideService(){
        Intent intent = new Intent(this, PreLoadService.class);
        this.startService(intent);
    }

    private void stopHideService(){
        Intent intent = new Intent(this, PreLoadService.class);
        this.stopService(intent);
    }

    /**
     * ????????????webview???list
     * @return
     */
    private ArrayList<String> getURLList(){
        ArrayList<String> url = new ArrayList<>();
        for(MyVideo.Videos videos:list){
            url.add(videos.getShare_url());
        }
        return url;
    }

    /**
     * ???????????????????????????
     *
     * @param duration    ????????????
     * @param timeUnit    ??????????????????
     * @param tag         ????????????
     * @param workerClass ????????? Work???
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

    @Override public void setErrorView() {
        RecyclerView recyclerView = getBinding().recyclerViewHome;
        if (recyclerView.getLayoutManager() == null){
           recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        View emptyView = EmptyViewUtil.getErrorView(recyclerView);
        if (mmkv.decodeBool(GlobalConstant.IS_LOGIN, false)){
            emptyView.setOnClickListener(v -> {
                presenter.getPersonInfo();
                presenter.getPersonalVideoList(0);
            });
        }

        if (adapter == null){
            adapter = new HomeAdapter(R.layout.reycylerview_item_home, null);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setList(null);
        }
        adapter.setEmptyView(emptyView);
    }

    /**
     * ???????????????????????????????????????clientToken???????????????
     *
     * @param event ?????????event??????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshClientToken(BaseEvent<String> event) {
        if (event.getEventCode() == EventCode.CLIENT_AGAIN)
            presenter.getClientToken();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBinding().homeTextViewFans.setText(mmkv.decodeInt(GlobalConstant.FANS_TOTAL, 0) + "??????");
        getBinding().homeTextViewFollower.setText(mmkv.decodeInt(GlobalConstant.FOLLOWINGS_TOTAL, 0) + "??????");
    }

    /**
     * ??????Toolbar???menu
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
     * ??????drawerLayout
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
}
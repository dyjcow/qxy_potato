package com.qxy.potato.module.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.bean.UserInfo;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.databinding.ActivityHomeBinding;
import com.qxy.potato.module.home.adapter.HomeAdapter;
import com.qxy.potato.module.home.adapter.HomeItemDecoration;
import com.qxy.potato.module.home.presenter.HomePresenter;
import com.qxy.potato.module.home.view.IHomeView;
import com.qxy.potato.module.mine.activity.LoginActivity;
import com.qxy.potato.module.videolist.activity.RankActivity;
import com.qxy.potato.util.DisplayUtil;



import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import com.tamsiree.rxui.view.dialog.RxDialogSure;
import com.tencent.mmkv.MMKV;


import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding>implements IHomeView {

    private MMKV mmkv=MMKV.defaultMMKV();
    private HomeAdapter adapter;

    
    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    List<MyVideo.Videos> list=new ArrayList<>();
    @Override
    protected void initView() {


        adapter=new HomeAdapter(R.layout.reycylerview_item_home,list);
        adapter.addChildClickViewIds(R.id.home_item_imageView);

        //recyclerview初始化
        getBinding().recyclerViewHome.addItemDecoration(new HomeItemDecoration(1,0,0,0));
        getBinding().recyclerViewHome.setLayoutManager(new GridLayoutManager(this,3));
        getBinding().recyclerViewHome.setAdapter( adapter);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.home_collapsing_toolbar);
        AppBarLayout appBarLayout=findViewById(R.id.appBar);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                // TODO: 2022/8/20  填入webViewActivity
                /*Intent intent=new Intent(HomeActivity.class,*//*webView*//*);
                intent.putExtra("shareUrl",list.get(position).getShare_url());
                startActivity(intent);*/
            }
        });


        //设置toolbar
        Toolbar toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        //折叠框的配置
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#003547"));

        //根据滑动监听来判断是否显示title
        //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {


            if(getSupportActionBar().getHeight()+DisplayUtil.dp2px(20)- appBarLayout1.getHeight()==verticalOffset){
                //折叠监听

                collapsingToolbarLayout.setTitle(" 作品");
                getBinding().homeIconSmall.setVisibility(View.VISIBLE);


                if (getBinding().homeToolbar.getMenu().findItem(R.id.open_rank)!=null) {
                    getBinding().homeToolbar.getMenu().findItem(R.id.open_rank).setIcon(R.mipmap.home_openrank);
                }

                //Toast.makeText(getApplicationContext(),"折叠了",Toast.LENGTH_SHORT).show();
            }else {   //展开监听
               // Toast.makeText(getApplicationContext(),"展开了",Toast.LENGTH_SHORT).show();
                getBinding().homeIconSmall.setVisibility(View.INVISIBLE);
                collapsingToolbarLayout.setTitle(" ");

                if (getBinding().homeToolbar.getMenu().findItem(R.id.open_rank)!=null) {
                    getBinding().homeToolbar.getMenu().findItem(R.id.open_rank).setIcon(R.mipmap.home_openrank2);
                }

            }
        });
        //点击小头像返回顶部
        getBinding().homeIconSmall.setOnClickListener(view -> getBinding().nestedScrollViewLayout.smoothScrollTo(0,0,200));
        //跳转到关注页
        // TODO: 2022/8/20 跳转到关注页 
        getBinding().homeTextViewFollower.setOnClickListener(view -> Toast.makeText(HomeActivity.this, "Followers", Toast.LENGTH_SHORT).show());
        //跳转到粉丝页
        // TODO: 2022/8/20 跳转到粉丝页 
        getBinding().homeTextViewFans.setOnClickListener(view -> Toast.makeText(HomeActivity.this, "Fans", Toast.LENGTH_SHORT).show());
        //点赞的dialog
        getBinding().homeTextViewLike.setOnClickListener(view -> {
            RxDialogSure rxDialogSure=new RxDialogSure(this);
            rxDialogSure.setLogo(R.drawable.ic_launcher_foreground);
            rxDialogSure.setSureListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rxDialogSure.cancel();
                }
            });
            rxDialogSure.setContent("你一共获得"+getLiked+"点赞");
            rxDialogSure.show();
        });
        //通过DrawerLayout打开榜单页面 和登录页
        getBinding().homeNavigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.nav_rank){
                startActivity(new Intent(HomeActivity.this, RankActivity.class));
            }else if (item.getItemId()==R.id.nav_login)
            {
                if (getBinding().homeNavigationView.getMenu().getItem(2).getTitle().equals("登录")) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }else {
                    mmkv.encode(GlobalConstant.IS_LOGIN,false);
                    Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            return true;
        });

        //下拉加载更多
         getBinding().homeRefreshlayout.setEnableRefresh(false);
         getBinding().homeRefreshlayout.setEnableLoadMore(true);
         getBinding().homeRefreshlayout.setRefreshFooter(new ClassicsFooter(this));
         getBinding().homeRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
             @Override
             public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                 if (isHasMore&&checkList.size()!=0) {
                     presenter.getPersonalVideoList(cursor);
                     refreshLayout.finishLoadMore(true);
                 }else {
                     refreshLayout.finishLoadMore(true);
                     refreshLayout.setEnableLoadMore(false);
                     getBinding().homeRecyclerviewFooter.setVisibility(View.VISIBLE);;
                 }
             }
         });



    }


    /**
     * 加载Toolbar的menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar,menu);
        return true;
    }

    /**
     * 打开drawerLayout
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.open_rank) {
            getBinding().drawerLayout.openDrawer(Gravity.RIGHT);
        }
        return true;
    }

    private long cursor=0;
    private boolean isHasMore=false;
    /**
     * 相关数据的初始化
     */
    @Override
    protected void initData() {
        presenter.getPersonInfo();
        presenter.getPersonalVideoList(cursor);
        // TODO: 2022/8/20 此处通过修改图片URL来配置背景图片（接口无） 
        Glide.with(this).load("https://www.keaidian.com/uploads/allimg/190424/24110307_23.jpg").into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                getBinding().homeBackground.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        boolean isLogin=mmkv.decodeBool(GlobalConstant.IS_LOGIN);
        if (isLogin){
            getBinding().homeNavigationView.getMenu().getItem(2).setTitle("登出");
        }else {
            getBinding().homeNavigationView.getMenu().getItem(2).setTitle("登录");
        }


    }



    @Override
    public void showPersonalInfo(UserInfo userInfo) {
        //getBinding().homeTextViewLike.setText(HomeAdapter.getLiked+"获赞");
        getBinding().homeTextViewFans.setText(0+"粉丝");
        getBinding().homeTextViewFollower.setText(0+"关注");
        getBinding().textViewIntroduce.setText("无");
        getBinding().homeTextviewSchool.setText("无");
        getBinding().homeTextviewPlace.setText((userInfo.getCountry() + userInfo.getDistrict()).equals("") ?"无":(userInfo.getCountry()+userInfo.getDistrict()));
        getBinding().homeTextviewAge.setText(userInfo.getGender()+"");
        Glide.with(this).load(userInfo.getAvatar()).into(getBinding().homeIconSmall);
        Glide.with(this).load(userInfo.getAvatar_larger()).into(getBinding().homeIcon);
        getBinding().textViwPersonalName.setText(userInfo.getNickname());


    }

    private List<MyVideo.Videos> checkList=new ArrayList<>();
    private int getLiked=0;
    @Override
    public void showPersonalVideo(List<MyVideo.Videos> videos, boolean isHasMore,long cursor) {
        this.isHasMore=isHasMore;
        this.cursor=cursor;
        if (videos!=null) {
            int position=list.size()-1;
            for (int i = 0; i < videos.size(); i++) {
                checkList.clear();
                if (!videos.get(i).getShare_url().equals(""))
                {
                    getLiked+=videos.get(i).getStatistics().getDigg_count();
                    checkList.add(videos.get(i));
                    adapter.addData(videos.get(i));
                }
            }
            getBinding().homeTextViewLike.setText(getLiked+"获赞");

            adapter.notifyItemChanged(position);
        }


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initData();

    }
}
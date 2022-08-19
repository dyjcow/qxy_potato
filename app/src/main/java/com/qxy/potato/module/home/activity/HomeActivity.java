package com.qxy.potato.module.home.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.qxy.potato.R;
import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.databinding.ActivityHomeBinding;
import com.qxy.potato.module.home.adapter.HomeAdapter;
import com.qxy.potato.module.home.adapter.HomeItemDecoration;
import com.qxy.potato.module.home.presenter.HomePresenter;
import com.qxy.potato.module.home.view.IHomeView;
import com.qxy.potato.module.videolist.activity.RankActivity;
import com.qxy.potato.util.DisplayUtil;



import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import com.tamsiree.rxui.view.dialog.RxDialog;
import com.tamsiree.rxui.view.dialog.RxDialogSure;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding>implements IHomeView {

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    private int count =0;
    @Override
    protected void initView() {

        List<String> list=new ArrayList<>();
        for (int i = 0; i <20; i++) {
            list.add("fwevds");
        }
        HomeAdapter adapter=new HomeAdapter(R.layout.reycylerview_item_home,list);

       // adapter.addHeaderView(LayoutInflater.from (this).inflate (R.layout.recyclerview_home_header, null));

        //recyclerview初始化
        getBinding().recyclerViewHome.addItemDecoration(new HomeItemDecoration(120,5,5,5));
        getBinding().recyclerViewHome.setLayoutManager(new GridLayoutManager(this,3));
        getBinding().recyclerViewHome.setAdapter( adapter);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.home_collapsing_toolbar);
        AppBarLayout appBarLayout=findViewById(R.id.appBar);
        //设置toolbar
        Toolbar toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        //折叠框的配置
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#003547"));

        //根据滑动监听来判断是否显示title
        //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {


            Log.d("onOffset", "onOffsetChanged: "+getSupportActionBar().getHeight()+"   "+ appBarLayout1.getHeight()+"  "+ verticalOffset+"  ");
            if(getSupportActionBar().getHeight()+DisplayUtil.dp2px(20)- appBarLayout1.getHeight()==verticalOffset){
                //折叠监听

                collapsingToolbarLayout.setTitle(" 作品");
                getBinding().homeIconSmall.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(),"折叠了",Toast.LENGTH_SHORT).show();
            }else {   //展开监听
               // Toast.makeText(getApplicationContext(),"展开了",Toast.LENGTH_SHORT).show();
                getBinding().homeIconSmall.setVisibility(View.INVISIBLE);
                collapsingToolbarLayout.setTitle(" ");
            }
        });
        //点击小头像返回顶部
        getBinding().homeIconSmall.setOnClickListener(view -> getBinding().nestedScrollViewLayout.smoothScrollTo(0,0,200));
        //跳转到关注页
        getBinding().homeTextViewFollower.setOnClickListener(view -> Toast.makeText(HomeActivity.this, "Follower", Toast.LENGTH_SHORT).show());
        //跳转到粉丝页
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
            rxDialogSure.setContent("你一共获得5984点赞");
            rxDialogSure.show();
        });
        //通过DrawerLayout打开榜单页面
        getBinding().homeNavigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.nav_rank){
                startActivity(new Intent(HomeActivity.this, RankActivity.class));
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

                 for (int i = 0; i < 10; i++) {
                     list.add("kkkk");
                 }
                 count++;
                 adapter.setData$com_github_CymChad_brvah(list);
                 //adapter.addData(newlist);
                 adapter.notifyDataSetChanged();
                 //getBinding().recyclerViewHome.scrollToPosition(list.size());
                 Log.d("listOf", "onLoadMore: "+list.size());
                 refreshLayout.finishLoadMore(true);

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

    /**
     * 相关数据的初始化
     */
    @Override
    protected void initData() {
        Glide.with(this).load("https://www.keaidian.com/uploads/allimg/190424/24110307_23.jpg").into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                getBinding().homeBackground.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

}
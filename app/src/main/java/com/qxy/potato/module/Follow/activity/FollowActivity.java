package com.qxy.potato.module.Follow.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qxy.potato.R;
import com.qxy.potato.module.Follow.fragment.FollowFragment;
import com.qxy.potato.util.ActivityUtil;

public class FollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        //通过type判断默认启动的标签位置
        switch (type) {
            case "Followings": {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment, new FollowFragment(FollowFragment.FOLLOWINGS)).addToBackStack(null).commit();
                break;
            }
            case "Fans":{
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment, new FollowFragment(FollowFragment.Fans)).addToBackStack(null).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //系统返回直接结束活动
        ActivityUtil.finishActivity(this);
    }
}
package com.qxy.potato.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qxy.potato.R;
import com.qxy.potato.module.home.activity.HomeActivity;
import com.qxy.potato.util.ActivityUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityUtil.startActivity(HomeActivity.class, true);

    }
}
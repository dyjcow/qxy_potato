package com.qxy.potatos.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qxy.potatos.R;
import com.qxy.potatos.module.home.activity.HomeActivity;
import com.qxy.potatos.util.ActivityUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityUtil.startActivity(HomeActivity.class, true);

    }
}
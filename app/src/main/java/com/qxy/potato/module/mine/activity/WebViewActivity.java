package com.qxy.potato.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.qxy.potato.R;
import com.qxy.potato.app.App;

import com.qxy.potato.module.mine.webview.MyWebView;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.ToastUtil;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import java.util.HashMap;

public class WebViewActivity extends AppCompatActivity {

    private static final int BACK_PRESSED_INTERVAL = 1500;//时间间隔
    private long mBackPressed;//用来记录按下时间，连点两次返回键才返回
    private String url;

    private LinearLayout mLinearLayout;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initUI();

//
//		String url = "https://www.iesdouyin.com/share/video/QDlWd0EzdWVMU2Q0aU5tKzVaOElvVU03ODBtRHFQUCtLUHBSMHFRT21MVkFYYi9UMDYwemRSbVlxaWczNTd6RUJRc3MrM2hvRGlqK2EwNnhBc1lGUkpRPT0=/?region=CN&mid=6753173704399670023&u_code=12h9je425&titleType=title";
////		String url = "https://haokan.baidu.com/v?pd=wisenatural&vid=5917029874381575350";


        if (url != null) {
            //创建以及设置WebView
            setWebView();

            mWebView.loadUrl(url);
        } else {
            ToastUtil.showToast("传递的url为空");
            finish();
        }


    }

    /**
     * 初始化
     */
    private void initUI() {
        mLinearLayout = findViewById(R.id.ll_video_display);


        //首次初始化冷启动优化,调用TBS初始化:
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        mBackPressed = System.currentTimeMillis();

        //开启硬件加速
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    /**
     * 动态创建WebView同时设置他
     */
    private void setWebView() {


        //避免WebView引起的内存泄漏
        //动态创建
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new MyWebView(App.getContext());
        mWebView.setLayoutParams(params);
        mLinearLayout.addView(mWebView);


    }

    //	/**
    //	 * 模拟物理点击控件中心处
    //	 *
    //	 * @param view
    //	 */
    //	private void centralClick(View view) {
    //		//左上角坐标    (left,top)
    //		int top = view.getTop();
    //		int left = view.getLeft();
    //		//右下角坐标
    //		int right = view.getRight();
    //		int bottom = view.getBottom();
    //		//中心
    //		int y = (bottom - top) / 2;
    //		int x = (right - left) / 2;
    //
    //		LogUtil.i("中心：" + x + " " + y);
    //
    //		long downTime = SystemClock.uptimeMillis();
    //		final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
    //				MotionEvent.ACTION_DOWN, x, y, 0);
    //		downTime += 1000;
    //		final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x,
    //				y, 0);
    //		view.onTouchEvent(downEvent);
    //		view.onTouchEvent(upEvent);
    //		downEvent.recycle();
    //		upEvent.recycle();

    //	}

    @Override
    public void onPause() {
        if (null != mWebView) {

            mWebView.onPause();

        }
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.resumeTimers();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.onResume();
        }

    }

    /**
     * 配置Webview的返回键,碎片切换要加到返回栈中
     */
    @Override
    public void onBackPressed() {


        if (mWebView != null && mWebView.isShown()) {

            if (mWebView.canGoBack()) {//WebView的向前
                mWebView.goBack();
            } else {
//					if(mBackPressed + BACK_PRESSED_INTERVAL > System.currentTimeMillis()){//连续两次才返回到个人碎片（1.5s内）
//						super.onBackPressed();
//						return;
//					}else{//提示
//						ToastUtil.showToast("再点一次返回键返回个人界面");
//						mBackPressed = System.currentTimeMillis();
//
//					}
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        if (null != mWebView) {

            mWebView.pauseTimers();

        }
        super.onStop();
    }

    /**
     * 销毁时先销毁移除webview,避免内存泄漏
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

        super.onDestroy();

    }

}
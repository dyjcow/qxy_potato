package com.qxy.potato.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.qxy.potato.R;
import com.qxy.potato.app.App;

import com.qxy.potato.module.mine.adapter.WVFragmentAdapter;
import com.qxy.potato.module.mine.webview.MyWebView;
import com.qxy.potato.util.MyUtil;
import com.qxy.potato.util.ToastUtil;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    private static final int BACK_PRESSED_INTERVAL = 1500;//时间间隔
    private long mBackPressed;//用来记录按下时间，连点两次返回键才返回
//    private String url;
//
//    private LinearLayout mLinearLayout;
//    private WebView mWebView;

    private ViewPager2 viewPager2;

    private List<String> url;
    private WVFragmentAdapter wvFragmentAdapter;
    private int position;//viewpager的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        url = intent.getStringArrayListExtra("url");
        position = intent.getIntExtra("position",0);

//        url = new ArrayList<>();
//        url.add("https://www.iesdouyin.com/share/video/7133780420058975488/?region=CN&mid=6820411788354390024&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title");
//        url.add("https://www.iesdouyin.com/share/video/7133782841233509632/?region=CN&mid=6705301107993840398&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title");
//        url.add("https://www.iesdouyin.com/share/video/7133781562700647680/?region=CN&mid=7133781597588966151&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title");
//        url.add("https://www.iesdouyin.com/share/video/7133779279103397160/?region=CN&mid=7133779299257469704&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title");
//        url.add("https://www.iesdouyin.com/share/video/7133777683246222626/?region=CN&mid=7133777715114576677&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title");



        initUI();

//
//		String url = "https://www.iesdouyin.com/share/video/QDlWd0EzdWVMU2Q0aU5tKzVaOElvVU03ODBtRHFQUCtLUHBSMHFRT21MVkFYYi9UMDYwemRSbVlxaWczNTd6RUJRc3MrM2hvRGlqK2EwNnhBc1lGUkpRPT0=/?region=CN&mid=6753173704399670023&u_code=12h9je425&titleType=title";
////		String url = "https://haokan.baidu.com/v?pd=wisenatural&vid=5917029874381575350";

//
//        if (url != null) {
//            //创建以及设置WebView
//            setWebView();
//
//            mWebView.loadUrl(url);
//        } else {
//            ToastUtil.showToast("传递的url为空");
//            finish();
//        }


    }

    /**
     * 初始化
     */
    private void initUI() {
//        mLinearLayout = findViewById(R.id.ll_video_display);

        viewPager2 = findViewById(R.id.vp2_webview);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        wvFragmentAdapter = new WVFragmentAdapter(this,url);

        // 可以不设置 因为默认是 -1 默认不进行预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        // 这个必须设置 不然仍然会启用预加载
        ((RecyclerView)viewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
        ((RecyclerView)viewPager2.getChildAt(0)).setItemViewCacheSize(0);

//        viewPager2.setOffscreenPageLimit(2);

        viewPager2.setAdapter(wvFragmentAdapter);
        viewPager2.setCurrentItem(position);




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

//    /**
//     * 动态创建WebView同时设置他
//     */
//    private void setWebView() {
//
//
//        //避免WebView引起的内存泄漏
//        //动态创建
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mWebView = new MyWebView(App.getContext());
//        mWebView.setLayoutParams(params);
//        mLinearLayout.addView(mWebView);
//
//    }

//
//
//    @Override
//    public void onPause() {
//        if (null != mWebView) {
//
//            mWebView.onPause();
//
//        }
//        super.onPause();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mWebView.resumeTimers();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            mWebView.onResume();
//        }
//
//    }
//
//    /**
//     * 配置Webview的返回键,碎片切换要加到返回栈中
//     */
//    @Override
//    public void onBackPressed() {
//
//
//        if (mWebView != null && mWebView.isShown()) {
//
//            if (mWebView.canGoBack()) {//WebView的向前
//                mWebView.goBack();
//            } else {
////					if(mBackPressed + BACK_PRESSED_INTERVAL > System.currentTimeMillis()){//连续两次才返回到个人碎片（1.5s内）
////						super.onBackPressed();
////						return;
////					}else{//提示
////						ToastUtil.showToast("再点一次返回键返回个人界面");
////						mBackPressed = System.currentTimeMillis();
////
////					}
//                super.onBackPressed();
//            }
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        if (null != mWebView) {
//
//            mWebView.pauseTimers();
//
//        }
//        super.onStop();
//    }
//
//    /**
//     * 销毁时先销毁移除webview,避免内存泄漏
//     */
//    @Override
//    public void onDestroy() {
//        if (mWebView != null) {
//            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
//            mWebView.destroy();
//            mWebView = null;
//        }
//
//        super.onDestroy();
//
//    }

}
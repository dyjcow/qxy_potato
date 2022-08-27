package com.qxy.potatos.module.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.qxy.potatos.R;

import com.qxy.potatos.module.mine.adapter.WVFragmentAdapter;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

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
        initUI();
    }

    /**
     * 初始化
     */
    private void initUI() {
        viewPager2 = findViewById(R.id.vp2_webview);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        wvFragmentAdapter = new WVFragmentAdapter(this,url);

        // 可以不设置 因为默认是 -1 默认不进行预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        // 这个必须设置 不然仍然会启用预加载
        ((RecyclerView)viewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
        ((RecyclerView)viewPager2.getChildAt(0)).setItemViewCacheSize(0);
        viewPager2.setAdapter(wvFragmentAdapter);
        viewPager2.setCurrentItem(position);

        //首次初始化冷启动优化,调用TBS初始化:
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);


        //开启硬件加速
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

}
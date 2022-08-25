package com.qxy.potato.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.didichuxing.doraemonkit.DoKit;

import com.qxy.potato.MyEventBusIndex;
import com.qxy.potato.R;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tamsiree.rxkit.RxTool;
import com.tencent.mmkv.MMKV;
import com.tencent.smtt.sdk.QbSdk;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ren.yale.android.retrofitcachelibrx2.RetrofitCache;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/24 12:42
 * @description： From Application
 * @modified By：
 * @version: 1.0
 */
public class App extends Application {

    private static Context context;

    private static String sCurProcessName = null;
    private String processName;
    private String packageName;

    /**
     * Set the base context for this ContextWrapper.  All calls will then be
     * delegated to the base context.  Throws
     * IllegalStateException if a base context has already been set.
     *
     * @param base The new base context for this wrapper.
     */
    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        processName = getCurProcessName(base);
        packageName = getPackageName();
    }

    private boolean isMainProcess() {
        return !TextUtils.isEmpty(packageName) && TextUtils.equals(packageName, processName);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (isMainProcess()){
            MMKV.initialize(this);
            MMKV.mmkvWithID("MyID", MMKV.SINGLE_PROCESS_MODE, GlobalConstant.MMKV_KEY);
            //载入Dokit监测
            new DoKit.Builder(this)
                    .productId(context.getString(R.string.value_dokit_pid))
                    .build();
            //使用订阅索引，加快编译速度
            EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
            // 抖音授权
            String clientkey = context.getString(R.string.value_client_key);
            DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));
            //初始化
            MyUtil.initialize(this);
            //设置UI工具
            RxTool.init(this);
            //网络缓存
            RetrofitCache.getInstance().init(this);
        }else {
            QbSdk.initX5Environment(getContext(), new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
                    // 内核初始化完成，可能为系统内核，也可能为系统内核
                }

                /**
                 * 预初始化结束
                 * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
                 * @param isX5 是否使用X5内核
                 */
                @Override
                public void onViewInitFinished(boolean isX5) {
                    LogUtil.i("是否使用腾讯内核：" + isX5);

                }
            });
        }

        //设置打印开关
        LogUtil.setIsLog(true);
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(ActivityUtil.getActivityLifecycleCallbacks());

    }

    public static Context getContext() {
        return context;
    }

    private static String getCurProcessName(Context context) {
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        sCurProcessName = getProcessName(android.os.Process.myPid());
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        try {
            int pid = android.os.Process.myPid();

            sCurProcessName = getProcessName(pid);
            if (!TextUtils.isEmpty(sCurProcessName)) {
                return sCurProcessName;
            }
            //获取系统的ActivityManager服务
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return sCurProcessName;
            }
            for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sCurProcessName;
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }

            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}

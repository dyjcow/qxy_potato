package com.qxy.potatos.http;

import android.util.Log;


import com.qxy.potatos.http.cookie.CookiesManager;
import com.qxy.potatos.util.ActivityUtil;
import com.qxy.potatos.util.MyUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ren.yale.android.retrofitcachelibrx2.intercept.CacheForceInterceptorNoNet;

/**
 * @author 梁爽
 * @create 2020/8/9 11:29
 */
public class Okhttp3Client {
    private volatile static Okhttp3Client okhttp3Client;
    private OkHttpClient okHttpClient;

    public Okhttp3Client() {
        //配置缓存 200m
        int cacheSize = 200 * 1024 * 1024;
        @SuppressWarnings("AliDeprecation") File cacheFile = new File(ActivityUtil.getCurrentActivity().getCacheDir(), "cache");
        if (!cacheFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheFile.mkdirs();
        }
        Cache cache = new Cache(cacheFile, cacheSize);

        //配置okHttp并设置时间、日志信息和cookies
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            try {
                String text = URLDecoder.decode(message, "utf-8");
                Log.e("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("OKHttp-----", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        this.okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .addInterceptor(new CacheForceInterceptorNoNet())
                .addInterceptor(new MoreBaseUrlInterceptor(API.getKeyUrl()))
//                .addInterceptor(httpLoggingInterceptor)
                //设置Cookie持久化
                .cookieJar(new CookiesManager(MyUtil.getApplication()))
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 单例调用
     *
     * @return RetrofitService
     */
    public static Okhttp3Client getInstance() {
        if (okhttp3Client == null) {
            synchronized (Object.class) {
                if (okhttp3Client == null) {
                    okhttp3Client = new Okhttp3Client();
                }
            }
        }
        return okhttp3Client;
    }

    /**
     * 获取OkHttpClient对象
     *
     * @return 获取OkHttpClient对象
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}

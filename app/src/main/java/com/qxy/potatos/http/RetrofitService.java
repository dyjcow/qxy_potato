package com.qxy.potatos.http;

import com.qxy.potatos.R;
import com.qxy.potatos.http.cookie.CookiesManager;
import com.qxy.potatos.http.gson.BaseConverterFactory;
import com.qxy.potatos.util.MyUtil;
import com.qxy.potatos.util.NetworkUtil;
import com.tamsiree.rxkit.view.RxToast;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Description : RetrofitService
 *
 * @author XuCanyou666
 * @date 2020/2/8
 */


public class RetrofitService {

    private volatile static RetrofitService apiRetrofit;
    //保证非wifi状态下的单次提示
    private static int tag;
    private API.SZApi apiServer;

    /**
     * 初始化retrofit
     */
    private RetrofitService() {

        //配置okHttp并设置时间、日志信息和cookies
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MoreBaseUrlInterceptor(API.getKeyUrl()))
                .addInterceptor(httpLoggingInterceptor)
                //设置超时时间
                .connectTimeout(15, TimeUnit.SECONDS)
                //设置Cookie持久化
                .cookieJar(new CookiesManager(MyUtil.getApplication()))
                .build();

        //加入缓存
//        OkHttpClient okHttpClient3 = Okhttp3Client.getInstance().getOkHttpClient();

        //关联okHttp并加上rxJava和Gson的配置和baseUrl
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API.BASE_URL)
                .build();
        tag = 0;
        apiServer = retrofit.create(API.SZApi.class);
    }

    /**
     * 单例调用
     *
     * @return RetrofitService
     */
    public static RetrofitService getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new RetrofitService();
                }
            }
        }
        if (tag == 0) {
            checkNetWork();
        }
        return apiRetrofit;
    }

    private static void checkNetWork() {
        if (!NetworkUtil.isNetworkAvailable(MyUtil.getApplication())
                || NetworkUtil.getNetworkType(MyUtil.getApplication()) == 0) {
            RxToast.warning(MyUtil.getString(R.string.disconnected_network));
        } else if (NetworkUtil.getNetworkType(MyUtil.getApplication()) != 1) {
            tag = 1;
            RxToast.info(MyUtil.getString(R.string.note_use));
        }
    }

    /**
     * 获取api对象
     *
     * @return api对象
     */
    public API.SZApi getApiService() {
        return apiServer;
    }

}

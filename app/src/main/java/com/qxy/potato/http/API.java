package com.qxy.potato.http;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.util.MyUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/25 15:38
 * @description：接口文件
 * @modified By：
 * @version: 1.0
 */
public class API {
    static final String BASE_URL = MyUtil.getString(R.string.url);


    /**
     * 初始化多url拦截器时，传入拦截器构造方法中
     *
     * @return 返回以Headers中的关键字为key，以url为value的map
     */
    static HashMap<String,String> getKeyUrl(){
        HashMap<String,String> keyUrl = new HashMap<>();
//        String GEO_URL = MyUtil.getString(R.string.geourl);
//        String M_URL = MyUtil.getString(R.string.mxzp);
//        keyUrl.put("geo",GEO_URL);
//        keyUrl.put("m",M_URL);
        return keyUrl;
    }


    /**
     * 非 BASE_URL 接口需要在 Headers 加入 urlName 字段
     * eg: @Headers("urlName:geo") 对应的值,在 getKeyUrl() 中设置
     */
    public interface SZApi {

        /**
         * 背景图片
         * @return 对应 observable
         */
        @Headers({"app_id:kvq0nvszkwmqqqbh","app_secret:N0V3S20vM0lCd1dzZkZJWFpaalRkdz09","urlName:m"})
        @GET("api/image/girl/list/random")
        Observable<BaseBean<List<PictureGirl>>> getPic();

    }
}

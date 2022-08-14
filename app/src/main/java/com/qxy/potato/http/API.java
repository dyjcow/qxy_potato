package com.qxy.potato.http;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.bean.AccessToken;
import com.qxy.potato.bean.ClientToken;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.util.MyUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
        String M_URL = MyUtil.getString(R.string.mxzp);
        keyUrl.put("m",M_URL);
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


        /**
         * 请求授权登录
         *
         * @param map 传入Body中的 HashMap
         * @return 对应的observable
         */
        @Headers({"Content-Type:application/x-www-form-urlencoded"})
        @FormUrlEncoded
        @POST("oauth/access_token/")
        Observable<BaseBean<AccessToken>> PostAccessToken(@FieldMap HashMap<String,String> map);


        @Headers({"Content-Type:multipart/form-data"})
        @FormUrlEncoded
        @POST("oauth/client_token/")
        Observable<BaseBean<ClientToken>> PostClientToken(@FieldMap HashMap<String,String> map);

        /**
         * 获取本周榜单
         *
         * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
         * @param token PostClientToken 中获取到的token
         * @return 对应的observable
         */
        @Headers({"Content-Type:application/json"})
        @GET("discovery/ent/rank/item/")
        Observable<BaseBean<VideoList>> GetVideoListNow(@Query("type") int type,
                                                        @Header("access-token") String token);


        /**
         * 获取本周榜单
         *
         * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
         * @param version 从其他地方获取到传入的的榜单版本
         * @param token PostClientToken 中获取到的token
         * @return 对应的observable
         */
        @Headers({"Content-Type:application/json"})
        @GET("discovery/ent/rank/item/")
        Observable<BaseBean<VideoList>> GetVideoListLast(@Query("type") int type,
                                                         @Query("version") int version,
                                                         @Header("access-token") String token);

        /**
         * 获取本周榜单
         *
         * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
         * @param count 每页数量
         * @param token PostClientToken 中获取到的token
         * @return 对应的observable
         */
        @Headers({"Content-Type:application/json"})
        @GET("discovery/ent/rank/version/")
        Observable<BaseBean<VideoList>> GetVideoVersion(@Query("type") int type,
                                                        @Query("count") int count,
                                                        @Header("access-token") String token);
    }
}

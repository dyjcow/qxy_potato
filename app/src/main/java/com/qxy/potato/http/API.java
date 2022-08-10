package com.qxy.potato.http;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.bean.AirQuality;
import com.qxy.potato.bean.CitySearch;
import com.qxy.potato.bean.DailyFeel;
import com.qxy.potato.bean.MoonRise;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.bean.PopularCity;
import com.qxy.potato.bean.WeatherDay;
import com.qxy.potato.bean.WeatherHours;
import com.qxy.potato.bean.WeatherNow;
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
         * 获取当前实时天气
         *
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @GET("v7/weather/now")
        Observable<BaseBean<WeatherNow>> getWeatherNow(@Query("location") String location);

        /**
         * 获取未来7天的天气信息
         *
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @GET("v7/weather/15d")
        Observable<BaseBean<List<WeatherDay>>> getWeatherDay(@Query("location") String location);

        /**
         * 获取未来24小时天气情况
         *
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @GET("v7/weather/24h")
        Observable<BaseBean<List<WeatherHours>>> getWeatherHours(@Query("location") String location);

        /**
         * 生活指数
         *
         * @param type 生活指数类型
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @GET("v7/indices/1d")
        Observable<BaseBean<List<DailyFeel>>> getDailyFeel(@Query("type") String type,@Query("location") String location);

        /**
         * 城市搜索
         *
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @Headers("urlName:geo")
        @GET("v2/city/lookup")
        Observable<BaseBean<List<CitySearch>>> getCitySearch(@Query("location") String location);

        /**
         * 获取热门城市
         *
         * @param number 热门城市数量
         * @param range 城市范围，world是世界，cn是中国
         * @return 对应 observable
         */
        @Headers("urlName:geo")
        @GET("v2/city/top")
        Observable<BaseBean<List<PopularCity>>> getPopularCity(@Query("number") String number,@Query("range") String range);

        /**
         * 日出日落
         *
         * @param location 位置代码或者经纬度信息
         * @param date 日期，最多可选择未来60天（包含今天）的数据。日期格式为yyyyMMdd
         * @return 对应 observable
         */
        @GET("v7/astronomy/sun")
        Observable<BaseBean<?>> getSunRise(@Query("location") String location,@Query("date") String date);

        /**
         * 月升月落
         *
         * @param location 位置代码或者经纬度信息
         * @param date 日期，最多可选择未来60天（包含今天）的数据。日期格式为yyyyMMdd
         * @return 对应 observable
         */
        @GET("v7/astronomy/moon")
        Observable<BaseBean<List<MoonRise>>> getMoonRise(@Query("location") String location,@Query("date") String date);

        /**
         * 获取空气质量
         *
         * @param location 位置代码或者经纬度信息
         * @return 对应 observable
         */
        @GET("v7/air/now")
        Observable<BaseBean<AirQuality>> getAirQuality(@Query("location") String location);


        /**
         * 背景图片
         * @return 对应 observable
         */
        @Headers({"app_id:kvq0nvszkwmqqqbh","app_secret:N0V3S20vM0lCd1dzZkZJWFpaalRkdz09","urlName:m"})
        @GET("api/image/girl/list/random")
        Observable<BaseBean<List<PictureGirl>>> getPic();

    }
}

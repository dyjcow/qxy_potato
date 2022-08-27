package com.qxy.potatos.http;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/29 10:30
 * @description：BaseUrl拦截器
 * @modified By：https://juejin.cn/post/6844903807944491015
 * @version: 1.0
 */
public class MoreBaseUrlInterceptor implements Interceptor {

    private final HashMap<String, String> keyUrl;

    public MoreBaseUrlInterceptor(HashMap<String, String> keyUrl) {
        this.keyUrl = keyUrl;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originalRequest
        Request originalRequest = chain.request();
        //获取老的url
        HttpUrl oldUrl = originalRequest.url();
        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();
        //获取头信息的集合如：base,geo
        List<String> urlNameList = originalRequest.headers("urlName");
        if (urlNameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("urlName");
            //获取头信息中配置的value,如：m或者mock
            String urlName = urlNameList.get(0);
            HttpUrl baseURL;
            //根据头信息中配置的value,来匹配新的base_url地址
            baseURL = HttpUrl.parse(Objects.requireNonNull(keyUrl.get(urlName)));
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .encodedPath(baseURL.encodedPath() +
                            oldUrl.encodedPath().substring(1, oldUrl.encodedPath().length()))
                    .port(baseURL.port())//端口
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        } else {
            Request reOriginalRequest = builder.url(oldUrl).build();
            return chain.proceed(reOriginalRequest);
        }

    }
}


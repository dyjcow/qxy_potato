package com.qxy.potato.http.gson;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseException;
import com.qxy.potato.util.MyUtil;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author yechao
 * @date 2019/11/18/018
 * Describe : 重写ResponseBodyConverter对json预处理
 */
public class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    /**
     * 超时
     */
    private static final int LOG_OUT_TIME = 500;

    BaseResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        try {
            // TODO: 2022/8/10 更改此处 
            JSONObject object = new JSONObject(jsonString);
            int code = object.getInt(MyUtil.getString(R.string.code));

            if (200 != code ) {
                if (code == 1) return adapter.fromJson(jsonString);
                String data;
                //错误信息
                if (code == LOG_OUT_TIME) {
                    data = MyUtil.getString(R.string.time_out);

                } else if (code == 0){
                    data = object.getString(MyUtil.getString(R.string.error_msg));
                } else {
                    data = MyUtil.getString(R.string.OTHER_MSG);
                }
                //异常处理
                throw new BaseException(code, data);
            }
            //正确返回整个json
            return adapter.fromJson(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
            //数据解析异常即json格式有变动
            throw new BaseException(MyUtil.getString(R.string.PARSE_ERROR_MSG));
        } finally {
            value.close();
        }
    }
}
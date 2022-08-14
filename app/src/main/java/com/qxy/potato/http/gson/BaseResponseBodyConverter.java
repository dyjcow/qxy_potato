package com.qxy.potato.http.gson;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseException;
import com.qxy.potato.util.LogUtil;
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

    BaseResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        try{
            JSONObject object = new JSONObject(jsonString);
            int code = object.getInt(MyUtil.getString(R.string.code));
            String data ;
            if (code == 1) return adapter.fromJson(jsonString);
            else  data = object.getString(MyUtil.getString(R.string.msg));
            throw new BaseException(code, data);
        }catch (JSONException ex){
            ex.printStackTrace();
            try {
                JSONObject object = new JSONObject(jsonString);
                int error_code = object.getJSONObject("data").getInt(MyUtil.getString(R.string.error_code));
                if (0 != error_code ) {
                    String data;
                    data = object.getJSONObject("data").getString(MyUtil.getString(R.string.error_msg));
                    //异常处理
                    throw new BaseException(error_code, data);
                }
                //正确返回整个json
                return adapter.fromJson(jsonString);

            } catch (JSONException e) {
                e.printStackTrace();
                //数据解析异常即json格式有变动
                throw new BaseException(MyUtil.getString(R.string.PARSE_ERROR_MSG));
            }

        }finally {
            value.close();
        }



    }
}
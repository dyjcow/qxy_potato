package com.qxy.potato.module.mine.presenter;



import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.AccessToken;
import com.qxy.potato.bean.BodyClient;
import com.qxy.potato.bean.ClientToken;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.mine.view.IMineView;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author ：Dyj
 * @date ：Created in 2022/8/11 22:39
 * @description：Mine的P层
 * @modified By：
 * @version: 1.0
 */
public class MinePresenter extends BasePresenter<IMineView> {
    public MinePresenter(IMineView baseView) {
        super(baseView);
    }
    MMKV mmkv = MMKV.defaultMMKV();

    /**
     * 获取到 AccessToken 并存储
     *
     * @param authCode 授权码
     */
    public void getAccessToken(String authCode){
        HashMap<String,String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.client_secret_k));
        map.put(MyUtil.getString(R.string.code),authCode);
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.authorization_code));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.client_key_k));
        addDisposable(apiServer.PostAccessToken(map),
                new BaseObserver<BaseBean<AccessToken>>(baseView, true) {
            @Override
            public void onSuccess(BaseBean<AccessToken> o) {

                mmkv.encode(GlobalConstant.ACCESS_TOKEN,o.data.getAccess_token());
                mmkv.encode(GlobalConstant.IS_LOGIN,true);
                baseView.loginSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.loginFailed(msg);
            }
        });
    }

    /**
     * 载入测试图片
     */
    public void loadImg(){
        addDisposable(apiServer.getPic(), new BaseObserver<BaseBean<List<PictureGirl>>>(baseView, false) {

            @Override
            public void onSuccess(BaseBean<List<PictureGirl>> o) {
                baseView.loadImg(o.data.get(0).getImageUrl());
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取 ClientToken 并存储
     */
    public void getClientToken(){
        HashMap<String,String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.client_secret_k));
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.client_key_k));
        addDisposable(apiServer.PostClientToken(map), new BaseObserver<BaseBean<ClientToken>>(baseView,false) {


            @Override
            public void onSuccess(BaseBean<ClientToken> o) {
                mmkv.encode(GlobalConstant.CLIENT_TOKEN,o.data.getAccess_token());
                mmkv.encode(GlobalConstant.IS_CLIENT,true);
//                LogUtil.d(o.getAccess_token());
                baseView.cancelClientValue();
            }

            @Override
            public void onError(String msg) {
//                LogUtil.d(msg+mmkv.decodeString(GlobalConstant.CLIENT_TOKEN));

            }
        });

    }
}

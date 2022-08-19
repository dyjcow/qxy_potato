package com.qxy.potato.module.mine.presenter;



import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.AccessToken;
import com.qxy.potato.bean.ClientToken;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.bean.PictureGirl;
import com.qxy.potato.bean.UserInfo;
import com.qxy.potato.bean.VideoVersion;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.mine.view.IMineView;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.List;


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
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.value_client_secret));
        map.put(MyUtil.getString(R.string.code),authCode);
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.authorization_code));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.value_client_key));
        addDisposable(apiServer.PostAccessToken(map),
                new BaseObserver<BaseBean<AccessToken>>(baseView, true) {
            @Override
            public void onSuccess(BaseBean<AccessToken> o) {

                mmkv.encode(GlobalConstant.ACCESS_TOKEN,o.data.getAccess_token());
                mmkv.encode(GlobalConstant.OPEN_ID,o.data.getOpen_id());
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
        HashMap<String,String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.app_id),MyUtil.getString(R.string.value_app_id));
        map.put(MyUtil.getString(R.string.app_secret),MyUtil.getString(R.string.value_app_secret));




        addDisposable(apiServer.getPic(map), new BaseObserver<BaseBean<List<PictureGirl>>>(baseView, false) {

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
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.value_client_secret));
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.value_client_key));
        addDisposable(apiServer.PostClientToken(map), new BaseObserver<BaseBean<ClientToken>>(baseView,false) {


            @Override
            public void onSuccess(BaseBean<ClientToken> o) {
                mmkv.encode(GlobalConstant.CLIENT_TOKEN,o.data.getAccess_token());
                mmkv.encode(GlobalConstant.IS_CLIENT,true);
                LogUtil.d(mmkv.decodeString(GlobalConstant.CLIENT_TOKEN));
                baseView.cancelClientValue();
            }

            @Override
            public void onError(String msg) {
//                LogUtil.d(msg+mmkv.decodeString(GlobalConstant.CLIENT_TOKEN));

            }
        });

    }

    public void getClientVersionDemo(){
        addDisposable(apiServer.GetVideoVersion(1,10,mmkv.decodeString(GlobalConstant.CLIENT_TOKEN)),
                new BaseObserver<BaseBean<VideoVersion>>(baseView, false) {
                    @Override
                    public void onSuccess(BaseBean<VideoVersion> o) {
                        List<VideoVersion.Version> list = o.data.getList();
                        list.get(0).setTag(EventCode.IS_FIRST_LIST);
                        MyUtil.showOneOptionPicker(list,"HistoryList");
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
    }

    public void test(){

        // TODO: 2022/8/19 接口测试全部通过，可保留此方法供使用者参考
        //接口测试代码
        HashMap<String,Integer> queryMap = new HashMap<>();
        HashMap<String,String> fieldMap = new HashMap<>();
        String token = mmkv.decodeString(GlobalConstant.ACCESS_TOKEN);
        String openId = mmkv.decodeString(GlobalConstant.OPEN_ID);
        queryMap.put(MyUtil.getString(R.string.cursor),0);
        queryMap.put(MyUtil.getString(R.string.count),10);
        fieldMap.put(MyUtil.getString(R.string.open_id),mmkv.decodeString(GlobalConstant.OPEN_ID));
        fieldMap.put(MyUtil.getString(R.string.access_token),token);

        addDisposable(apiServer.GetMyFans(token,openId,queryMap),
                new BaseObserver<BaseBean<Fans>>(baseView, false) {
            @Override
            public void onSuccess(BaseBean<Fans> o) {

            }

            @Override
            public void onError(String msg) {

            }
        });

        addDisposable(apiServer.GetMyInfo(fieldMap), new BaseObserver<BaseBean<UserInfo>>(baseView, false) {
            @Override
            public void onSuccess(BaseBean<UserInfo> o) {

            }

            @Override
            public void onError(String msg) {
                LogUtil.d(msg);
            }
        });

        addDisposable(apiServer.GetMyVideos(token,openId,queryMap), new BaseObserver<BaseBean<MyVideo>>(baseView, false) {
            @Override
            public void onSuccess(BaseBean<MyVideo> o) {

            }

            @Override
            public void onError(String msg) {
                LogUtil.d(msg);
            }
        });

        addDisposable(apiServer.GetMyFollowings(token,openId,queryMap), new BaseObserver<BaseBean<Followings>>(baseView, false) {
            @Override
            public void onSuccess(BaseBean<Followings> o) {

            }

            @Override
            public void onError(String msg) {

            }
        });




    }
}

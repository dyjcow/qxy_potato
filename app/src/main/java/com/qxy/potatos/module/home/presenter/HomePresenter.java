package com.qxy.potatos.module.home.presenter;

import android.content.Context;
import android.widget.Toast;

import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseBean;
import com.qxy.potatos.base.BaseObserver;
import com.qxy.potatos.base.BasePresenter;
import com.qxy.potatos.bean.ClientToken;
import com.qxy.potatos.bean.MyVideo;
import com.qxy.potatos.bean.UserInfo;
import com.qxy.potatos.common.GlobalConstant;
import com.qxy.potatos.module.home.view.IHomeView;
import com.qxy.potatos.module.home.workmanager.ClientCancelWork;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.MyUtil;
import com.tamsiree.rxkit.view.RxToast;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.homePresenter
 * @date :2022/8/16 21:20
 */
public class HomePresenter extends BasePresenter<IHomeView> {
    MMKV mmkv = MMKV.defaultMMKV();
    String access_token = mmkv.decodeString(GlobalConstant.ACCESS_TOKEN, null);
    String openId = mmkv.decodeString(GlobalConstant.OPEN_ID, null);
    Boolean isLogin = mmkv.decodeBool(GlobalConstant.IS_LOGIN, false);
    public HomePresenter(IHomeView baseView) {
        super(baseView);
    }

    /**
     * 获取主页个人信息
     */
    public void getPersonInfo() {
        isLogin = mmkv.decodeBool(GlobalConstant.IS_LOGIN, false);
        if (!isLogin) {
            RxToast.error("请先登录");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.open_id), openId);
        map.put(MyUtil.getString(R.string.access_token), access_token);
        addDisposable(apiServer.GetMyInfo(map), new BaseObserver<BaseBean<UserInfo>>(baseView, false) {

            @Override
            public void onError(String msg) {
                Toast.makeText((Context) baseView, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(BaseBean<UserInfo> o) {
                baseView.showPersonalInfo(o.data);
            }
        });

    }

    /**
     * 获取视频列表
     *
     * @param cursor
     */
    public void getPersonalVideoList(long cursor) {
        if (!isLogin) {
            return;
        }
        HashMap<String, Long> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.cursor), cursor);
        map.put(MyUtil.getString(R.string.count), (long) 12);
        addDisposable(apiServer.GetMyVideos(access_token, openId, map), new BaseObserver<BaseBean<MyVideo>>(baseView, true) {
            @Override
            public void onError(String msg) {

                Toast.makeText((Context) baseView, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(BaseBean<MyVideo> o) {
                baseView.showPersonalVideo(o.data.getList(), o.data.isHas_more(), o.data.getCursor());
            }
        });


    }


    /**
     * 获取 ClientToken 并存储
     */
    public void getClientToken() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.value_client_secret));
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.value_client_key));
        addDisposable(apiServer.PostClientToken(map), new BaseObserver<BaseBean<ClientToken>>(baseView, false) {
            @Override
            public void onError(String msg) {
//                LogUtil.d(msg+mmkv.decodeString(GlobalConstant.CLIENT_TOKEN));

            }

            @Override
            public void onSuccess(BaseBean<ClientToken> o) {
                mmkv.encode(GlobalConstant.CLIENT_TOKEN, o.data.getAccess_token());
                mmkv.encode(GlobalConstant.IS_CLIENT, true);
                LogUtil.d(mmkv.decodeString(GlobalConstant.CLIENT_TOKEN));
                //两小时后取消连接状态
                baseView.startWork(120, TimeUnit.MINUTES,
                        GlobalConstant.CLIENT_TOKEN, ClientCancelWork.class);
            }
        });

    }
}

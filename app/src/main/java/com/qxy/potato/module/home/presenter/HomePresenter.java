package com.qxy.potato.module.home.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.MyVideo;
import com.qxy.potato.bean.UserInfo;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.home.view.IHomeView;
import com.qxy.potato.util.MyUtil;
import com.tamsiree.rxkit.view.RxToast;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.homePresenter
 * @date :2022/8/16 21:20
 */
public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(IHomeView baseView) {
        super(baseView);
    }

    MMKV mmkv=MMKV.defaultMMKV();
    String access_token=mmkv.decodeString(GlobalConstant.ACCESS_TOKEN,null);
    String openId=mmkv.decodeString(GlobalConstant.OPEN_ID,null);
    Boolean isLogin=mmkv.decodeBool(GlobalConstant.IS_LOGIN,false);


    /**
     * 获取主页个人信息
     */
    public void getPersonInfo(){

        isLogin=mmkv.decodeBool(GlobalConstant.IS_LOGIN,false);
        if (!isLogin){
            RxToast.error("请先登录");
            return;
        }

        HashMap<String,String> map=new HashMap();
        map.put(MyUtil.getString(R.string.open_id),openId);
        map.put(MyUtil.getString(R.string.access_token),access_token);
        addDisposable(apiServer.GetMyInfo(map), new BaseObserver<BaseBean<UserInfo>>(baseView,false) {
            @Override
            public void onSuccess(BaseBean<UserInfo> o) {
                baseView.showPersonalInfo(o.data);
            }
            @Override
            public void onError(String msg) {
                Toast.makeText((Context) baseView, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 获取视频列表
     * @param cursor
     */
    public void getPersonalVideoList(long cursor){
        if (!isLogin){
            return;
        }
        HashMap<String,Long> map=new HashMap<>();
        map.put("cursor",cursor);
        map.put("count",(long)5);
        addDisposable(apiServer.GetMyVideos(access_token, openId, map), new BaseObserver<BaseBean<MyVideo>>(baseView,false) {
            @Override
            public void onSuccess(BaseBean<MyVideo> o) {
                baseView.showPersonalVideo(o.data.getList(),o.data.isHas_more(),o.data.getCursor());
            }
            @Override
            public void onError(String msg) {
                Toast.makeText((Context) baseView, msg, Toast.LENGTH_SHORT).show();
            }
            });



    }
}

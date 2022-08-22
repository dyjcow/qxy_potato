package com.qxy.potato.module.videorank.presenter;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.ClientToken;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.bean.VideoVersion;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.videorank.view.IVideoRankView;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 16:31
 * @description：VideoRank的presenter层
 * @modified By：
 * @version: 1.0
 */
public class VideoRankPresenter extends BasePresenter<IVideoRankView> {

    public VideoRankPresenter(IVideoRankView baseView) {
        super(baseView);
    }

    MMKV mmkv = MMKV.defaultMMKV();

    /**
     *获取本周榜单
     * @param type * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     */
    public void getNowRank(int type){
        //获取到token
        if (MMKV.defaultMMKV().decodeBool(GlobalConstant.IS_CLIENT,false)){
            String token = MMKV.defaultMMKV().decodeString(GlobalConstant.CLIENT_TOKEN);
            LogUtil.i(token);
            addDisposable(apiServer.GetVideoListNow(type, token), new BaseObserver<BaseBean<VideoList>>(baseView,false) {
                @Override public void onSuccess(BaseBean<VideoList> o) {
                    baseView.showRankSuccess(o.data,-1);
                }

                @Override public void onError(String msg) {
                    LogUtil.d("获取最近榜单错误信息："+msg);
                    baseView.showRankFailed(msg);
                }
            });

        }else{
            //记录
            getClientToken(type,-1);
        }

    }


    public void getLastVersionRank(int type,int version){
        if (MMKV.defaultMMKV().decodeBool(GlobalConstant.IS_CLIENT,false)){
            String token = MMKV.defaultMMKV().decodeString(GlobalConstant.CLIENT_TOKEN);
            LogUtil.i(token);
            addDisposable(apiServer.GetVideoListLast(type,version, token), new BaseObserver<BaseBean<VideoList>>(baseView,false) {
                @Override public void onSuccess(BaseBean<VideoList> o) {
                    baseView.showRankSuccess(o.data,version);
                }

                @Override public void onError(String msg) {
                    LogUtil.d("获取最近榜单错误信息："+msg);
                    baseView.showRankFailed(msg);
                }
            });

        }else{
            //记录
            getClientToken(type,version);
        }
    }



    /**
     * 获取 ClientToken 并存储
     * @param type  榜单类型
     * @param version 榜单版本
     */
    private void getClientToken(int type,int version){
        HashMap<String,String> map = new HashMap<>();
//        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.value_client_secret));
//        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
//        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.value_client_key));
        map.put(MyUtil.getString(R.string.client_secret),"3cd85c00cec461ef3ed8e17a4f244f42");
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
        map.put(MyUtil.getString(R.string.client_key),"awf6mbxfmd4r795y");
        addDisposable(apiServer.PostClientToken(map), new BaseObserver<ClientToken>(baseView,false) {


            @Override
            public void onSuccess(ClientToken o) {
                mmkv.encode(GlobalConstant.CLIENT_TOKEN,o.getAccess_token());
                mmkv.encode(GlobalConstant.IS_CLIENT,true);

                //再次请求
                if (version == -1){
                    getNowRank(type);
                }else {
                    getLastVersionRank(type,version);
                }
            }

            @Override
            public void onError(String msg) {
                baseView.showRankFailed("token获取失败"+msg);

            }
        });
    }

    public void getClientVersion(int type){
        addDisposable(apiServer.GetVideoVersion(type,20,mmkv.decodeString(GlobalConstant.CLIENT_TOKEN)),
                new BaseObserver<BaseBean<VideoVersion>>(baseView, true) {
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
}

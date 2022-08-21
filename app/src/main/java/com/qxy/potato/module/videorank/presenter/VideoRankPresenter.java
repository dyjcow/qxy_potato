package com.qxy.potato.module.videorank.presenter;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.ClientToken;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.videorank.view.IVideoRankView;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

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


                    baseView.showRankSuccess(o.data);
                }

                @Override public void onError(String msg) {
                    LogUtil.d("获取最近榜单错误信息："+msg);
                    baseView.showRankFailed(msg);
                }
            });

        }else{
            //记录
            getClientToken(type);
        }



    }



    /**
     * 获取 ClientToken 并存储
     * @param tmp
     */
    private void getClientToken(int tmp){
        HashMap<String,String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.client_secret),MyUtil.getString(R.string.value_client_secret));
        map.put(MyUtil.getString(R.string.grant_type),MyUtil.getString(R.string.client_credential));
        map.put(MyUtil.getString(R.string.client_key),MyUtil.getString(R.string.value_client_key));
        addDisposable(apiServer.PostClientToken(map), new BaseObserver<ClientToken>(baseView,false) {


            @Override
            public void onSuccess(ClientToken o) {
                mmkv.encode(GlobalConstant.CLIENT_TOKEN,o.getAccess_token());
                mmkv.encode(GlobalConstant.IS_CLIENT,true);

                //再次请求
                getNowRank(tmp);

            }

            @Override
            public void onError(String msg) {
                baseView.showRankFailed("token获取失败"+msg);

            }
        });
    }
}
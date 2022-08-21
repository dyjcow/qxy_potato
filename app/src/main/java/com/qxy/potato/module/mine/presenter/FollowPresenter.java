package com.qxy.potato.module.mine.presenter;

import com.qxy.potato.R;
import com.qxy.potato.base.BaseBean;
import com.qxy.potato.base.BaseObserver;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.module.mine.view.IFollowView;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

/**
 * @author ：potato
 * @date ：Created in 2022/8/18 22:56
 * @description：关注粉丝页面的P层
 * @modified By：
 * @version: 1.0
 */
public class FollowPresenter extends BasePresenter<IFollowView> {

    private MMKV mmkv = MMKV.defaultMMKV();
    private String token = mmkv.decodeString(GlobalConstant.ACCESS_TOKEN);
    private String openId = mmkv.decodeString(GlobalConstant.OPEN_ID);

    public FollowPresenter(IFollowView baseView) {
        super(baseView);
    }

    public void getFollowingsList(int cursor, int count){
        HashMap<String,Integer> queryMap = new HashMap<>();
        queryMap.put(MyUtil.getString(R.string.cursor),cursor);
        queryMap.put(MyUtil.getString(R.string.count),count);

        addDisposable(apiServer.GetMyFollowings(token, openId, queryMap),
                new BaseObserver<BaseBean<Followings>>(baseView,true) {

                    @Override
                    public void onSuccess(BaseBean<Followings> o) {
                        baseView.showFollowingsList(o.data);
                    }

                    @Override
                    public void onError(String msg) {
                        LogUtil.e("获取关注列表失败：",msg);
                        baseView.loadFail(msg);
                    }
        });
    }

    public void getFansList(int cursor, int count){
        HashMap<String,Integer> queryMap = new HashMap<>();
        queryMap.put(MyUtil.getString(R.string.cursor),cursor);
        queryMap.put(MyUtil.getString(R.string.count),count);

        addDisposable(apiServer.GetMyFollowings(token, openId, queryMap),
                new BaseObserver<BaseBean<Fans>>(baseView,true) {

                    @Override
                    public void onSuccess(BaseBean<Fans> o) {
                        baseView.showFansList(o.data);
                    }

                    @Override
                    public void onError(String msg) {
                        LogUtil.e("获取粉丝列表失败：",msg);
                        baseView.loadFail(msg);
                    }
                });
    }

}

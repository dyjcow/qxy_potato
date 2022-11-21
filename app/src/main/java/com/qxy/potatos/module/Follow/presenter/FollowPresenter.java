package com.qxy.potatos.module.Follow.presenter;

import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseBean;
import com.qxy.potatos.base.BaseObserver;
import com.qxy.potatos.base.BasePresenter;
import com.qxy.potatos.bean.Fans;
import com.qxy.potatos.bean.Followings;
import com.qxy.potatos.common.GlobalConstant;
import com.qxy.potatos.module.Follow.view.IFollowView;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.MyUtil;
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

    private final MMKV mmkv = MMKV.defaultMMKV();
    private final String token = mmkv.decodeString(GlobalConstant.ACCESS_TOKEN);
    private final String openId = mmkv.decodeString(GlobalConstant.OPEN_ID);

    public FollowPresenter(IFollowView baseView) {
        super(baseView);
    }

    public void getFollowingsList(int cursor, int count) {
        HashMap<String, Integer> queryMap = new HashMap<>();
        queryMap.put(MyUtil.getString(R.string.cursor), cursor);
        queryMap.put(MyUtil.getString(R.string.count), count);
        addDisposable(apiServer.GetMyFollowings(token, openId, queryMap),
                new BaseObserver<BaseBean<Followings>>(baseView, false) {

                    @Override
                    public void onError(String msg) {
                        LogUtil.e("获取关注列表失败：", msg);
                        baseView.setFailView();
                        baseView.loadFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseBean<Followings> o) {
                        baseView.showFollowingsList(o.data);
                    }
                });
    }

    public void getFansList(int cursor, int count) {
        HashMap<String, Integer> queryMap = new HashMap<>();
        queryMap.put(MyUtil.getString(R.string.cursor), cursor);
        queryMap.put(MyUtil.getString(R.string.count), count);

        addDisposable(apiServer.GetMyFans(token, openId, queryMap),
                new BaseObserver<BaseBean<Fans>>(baseView, false) {

                    @Override
                    public void onError(String msg) {
                        LogUtil.e("获取粉丝列表失败：", msg);
                        baseView.setFailView();
                        baseView.loadFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseBean<Fans> o) {
                        baseView.showFansList(o.data);
                    }
                });
    }

}

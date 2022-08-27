package com.qxy.potatos.module.mine.presenter;

import com.qxy.potatos.R;
import com.qxy.potatos.base.BaseBean;
import com.qxy.potatos.base.BaseObserver;
import com.qxy.potatos.base.BasePresenter;
import com.qxy.potatos.bean.AccessToken;
import com.qxy.potatos.common.GlobalConstant;
import com.qxy.potatos.module.mine.view.ILoginView;
import com.qxy.potatos.util.LogUtil;
import com.qxy.potatos.util.MyUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/13 16:08
 * @description：登录页面的P层
 * @modified By：
 * @version: 1.0
 */
public class LoginPresenter extends BasePresenter<ILoginView> {
    MMKV mmkv = MMKV.defaultMMKV();

    public LoginPresenter(ILoginView baseView) {
        super(baseView);
    }

    /**
     * 获取到 AccessToken 并存储
     *
     * @param authCode 授权码
     */
    public void getAccessToken(String authCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MyUtil.getString(R.string.client_secret), MyUtil.getString(R.string.value_client_secret));
        map.put(MyUtil.getString(R.string.code), authCode);
        map.put(MyUtil.getString(R.string.grant_type), MyUtil.getString(R.string.authorization_code));
        map.put(MyUtil.getString(R.string.client_key), MyUtil.getString(R.string.value_client_key));
        addDisposable(apiServer.PostAccessToken(map),
                new BaseObserver<BaseBean<AccessToken>>(baseView, true) {
                    @Override
                    public void onError(String msg) {
                        baseView.loginFailed(msg);
                    }

                    @Override
                    public void onSuccess(BaseBean<AccessToken> o) {
                        mmkv.encode(GlobalConstant.ACCESS_TOKEN, o.data.getAccess_token());
                        mmkv.encode(GlobalConstant.REFRESH_TOKEN, o.data.getRefresh_token());
                        mmkv.encode(GlobalConstant.OPEN_ID, o.data.getOpen_id());
                        mmkv.encode(GlobalConstant.IS_LOGIN, true);
                        LogUtil.d(o.data.getAccess_token());
                        baseView.loginSuccess();
                    }
                });
    }


}

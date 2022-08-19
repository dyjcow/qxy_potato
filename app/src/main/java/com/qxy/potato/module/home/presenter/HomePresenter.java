package com.qxy.potato.module.home.presenter;

import com.qxy.potato.base.BaseActivity;
import com.qxy.potato.base.BasePresenter;
import com.qxy.potato.databinding.ActivityHomeBinding;
import com.qxy.potato.module.home.activity.HomeActivity;
import com.qxy.potato.module.home.view.IHomeView;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.homePresenter
 * @date :2022/8/16 21:20
 */
public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(IHomeView baseView) {
        super(baseView);
    }
}

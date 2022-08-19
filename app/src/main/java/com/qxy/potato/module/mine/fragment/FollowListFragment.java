package com.qxy.potato.module.mine.fragment;

import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.databinding.LinearlayoutMineFollowListBinding;
import com.qxy.potato.module.mine.presenter.FollowPresenter;
import com.qxy.potato.module.mine.view.IFollowView;

public class FollowListFragment extends BaseFragment<FollowPresenter, LinearlayoutMineFollowListBinding> implements IFollowView {
    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

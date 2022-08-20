package com.qxy.potato.module.mine.fragment;

import android.app.Activity;

import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.databinding.LinearlayoutMineFollowListBinding;
import com.qxy.potato.module.mine.presenter.FollowPresenter;
import com.qxy.potato.module.mine.view.IFollowView;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

public class FollowListFragment extends BaseFragment<FollowPresenter, LinearlayoutMineFollowListBinding> implements IFollowView {
    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter(this);
    }

    private Activity activity=ActivityUtil.getCurrentActivity();

    private RefreshLayout refreshLayout;

    private List<Followings.Following> loadFollowings=new LinkedList<>();
    private List<Fans.Fan> loadFans=new LinkedList<>();

    private int followingCursor=0;
    private int fanCursor=0;

    @Override
    protected void initView() {
        refreshLayout = getBinding().smartlayout;
        refreshLayout.setRefreshHeader(new MaterialHeader(activity));
        refreshLayout.setRefreshFooter(new ClassicsFooter(activity));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                followingCursor=0;
                presenter.getFollowingsList(followingCursor,10);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.getFollowingsList(followingCursor,10);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getFollowingsList(0,10);
        presenter.getFansList(0,10);
    }

    @Override
    public void showFollowingsList(Followings followings) {
        if(loadFollowings.size()!=0){
            refreshLayout.finishRefresh();
        }
        loadFollowings.addAll(followings.getList());
        followingCursor=followings.getCursor();
    }

    @Override
    public void showFansList(Fans fans) {
        if(loadFollowings.size()!=0){
            refreshLayout.finishRefresh();
        }
        loadFans.addAll(fans.getList());
        fanCursor=fans.getCursor();
    }

    @Override
    public void loadFail(String msg) {
        if(loadFollowings.size()!=0){
            refreshLayout.finishRefresh(false);
        }
        ToastUtil.showToast(msg);
    }
}

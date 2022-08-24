package com.qxy.potato.module.Follow.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.common.GlobalConstant;
import com.qxy.potato.databinding.LinearlayoutMineFollowListBinding;
import com.qxy.potato.module.Follow.adapter.FansRecycleViewAdapter;
import com.qxy.potato.module.Follow.adapter.FollowingsRecycleViewAdapter;
import com.qxy.potato.module.Follow.presenter.FollowPresenter;
import com.qxy.potato.module.Follow.view.IFollowView;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.tencent.mmkv.MMKV;

public class FollowListFragment extends BaseFragment<FollowPresenter, LinearlayoutMineFollowListBinding> implements IFollowView {
    private Activity activity = ActivityUtil.getCurrentActivity();
    //刷新布局（非常好用）
    private RefreshLayout refreshLayout;
    //用户总数（关注的我隐藏了，不好获取）
    private TextView total;
    private RecyclerView recyclerView;
    //用于判断能否上拉加载
    private boolean followingsHasMore;
    private boolean fansHasMore;
    private MMKV mmkv = MMKV.defaultMMKV();
    //便于下次请求
    private int followingCursor = 0;
    private int fanCursor = 0;
    private int mType;
    //通过type进行相应操作，复用fragment
    public FollowListFragment(int type) {
        super();
        mType = type;
    }

    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter(this);
    }

    @Override
    protected void initView() {

        refreshLayout = getBinding().smartlayout;
        refreshLayout.setRefreshHeader(new MaterialHeader(activity));
        refreshLayout.setRefreshFooter(new ClassicsFooter(activity));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            switch (mType) {
                case FollowFragment.FOLLOWINGS: {
                    //每次刷新重新加载
                    recyclerView.setAdapter(null);
                    followingCursor = 0;
                    presenter.getFollowingsList(followingCursor, 12);
                    break;
                }
                case FollowFragment.Fans: {
                    recyclerView.setAdapter(null);
                    fanCursor = 0;
                    presenter.getFansList(fanCursor, 12);
                    break;
                }
                default:
                    break;
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            switch (mType) {
                case FollowFragment.FOLLOWINGS: {
                    //判断有无更多，有则通过followingCursor发起请求
                    if (followingsHasMore)
                        presenter.getFollowingsList(followingCursor, 12);
                    else
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    break;
                }
                case FollowFragment.Fans: {
                    //判断有无更多，有则通过fanCursor发起请求
                    if (fansHasMore)
                        presenter.getFansList(fanCursor, 12);
                    else
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    break;
                }
                default:
                    break;
            }
        });

        total = getBinding().textviewFollowTotal;
        if (mType == FollowFragment.FOLLOWINGS)
            total.setVisibility(View.INVISIBLE);

        recyclerView = getBinding().recyclerviewFollowList;
        //指定布局方式
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        //获取初始12条数据
        switch (mType) {
            case FollowFragment.FOLLOWINGS: {
                presenter.getFollowingsList(followingCursor, 12);
                break;
            }
            case FollowFragment.Fans: {
                presenter.getFansList(fanCursor, 12);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void showFollowingsList(Followings followings) {
        //判断防止空指针
        if (followings.getList() != null) {
            //通过判断适配器是否设置，从而知道是第一次还是后来的加载
            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(new FollowingsRecycleViewAdapter(followings.getList()));
                //存储，每次重启可能会导致关注数的上下浮动
                mmkv.encode(GlobalConstant.FOLLOWINGS_TOTAL, followings.getList().size());
            } else {
                FollowingsRecycleViewAdapter adapter = (FollowingsRecycleViewAdapter) recyclerView.getAdapter();
                adapter.addData(followings.getList());
                //因为关注总数的特殊性，累加
                mmkv.encode(GlobalConstant.FOLLOWINGS_TOTAL, followings.getList().size() + mmkv.getInt(GlobalConstant.FOLLOWINGS_TOTAL, 0));
            }
        }
        //判断是否有动画，有则结束
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
        if (refreshLayout.isLoading())
            refreshLayout.finishLoadMore();
        followingsHasMore = followings.isHas_more();
        followingCursor = followings.getCursor();
    }

    @Override
    public void showFansList(Fans fans) {
        //判断防止空指针
        if (fans.getList() != null) {
            //通过判断适配器是否设置，从而知道是第一次还是后来的加载
            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(new FansRecycleViewAdapter(fans.getList()));
            } else {
                FansRecycleViewAdapter adapter = (FansRecycleViewAdapter) recyclerView.getAdapter();
                adapter.addData(fans.getList());
            }
        }
        //判断是否有动画，有则结束
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
        if (refreshLayout.isLoading())
            refreshLayout.finishLoadMore();
        fansHasMore = fans.isHas_more();
        fanCursor = fans.getCursor();
        //存储粉丝总数
        mmkv.encode(GlobalConstant.FANS_TOTAL, fans.getTotal());
        total.setText("我的粉丝(" + fans.getTotal() + "人)");
    }

    @Override
    public void loadFail(String msg) {
        //判断是否有动画，有则结束
        if (refreshLayout.isRefreshing() || refreshLayout.isLoading()) {
            refreshLayout.finishRefresh(false);
        }
        ToastUtil.showToast(msg);
    }
}

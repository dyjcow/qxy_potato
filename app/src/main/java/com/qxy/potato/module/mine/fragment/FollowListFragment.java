package com.qxy.potato.module.mine.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.Fans;
import com.qxy.potato.bean.Followings;
import com.qxy.potato.databinding.LinearlayoutMineFollowListBinding;
import com.qxy.potato.module.mine.adapter.FansRecycleViewAdapter;
import com.qxy.potato.module.mine.adapter.FollowingsRecycleViewAdapter;
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
    public FollowListFragment(int type) {
        super();
        mType=type;
    }

    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter(this);
    }

    private Activity activity=ActivityUtil.getCurrentActivity();

    private RefreshLayout refreshLayout;

    private TextView total;

    private RecyclerView list;

    private LinearLayoutManager manager;

//    private FollowingsRecycleViewAdapter followingsAdapter;
//    private FansRecycleViewAdapter fansAdapter;

//    private List<Followings.Following> loadFollowings=new LinkedList<>();
//    private List<Fans.Fan> loadFans=new LinkedList<>();

    private int followingCursor=0;
    private int fanCursor=0;

    private int mType;
    @Override
    protected void initView() {

        refreshLayout = getBinding().smartlayout;
        refreshLayout.setRefreshHeader(new MaterialHeader(activity));
        refreshLayout.setRefreshFooter(new ClassicsFooter(activity));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                switch (mType){
                    case FollowFragment.FOLLOWINGS:{
                        list.setAdapter(null);
                        followingCursor=0;
                        presenter.getFollowingsList(followingCursor,12);
                        break;
                    }
                    case FollowFragment.Fans:{
                        list.setAdapter(null);
                        fanCursor=0;
                        presenter.getFansList(fanCursor,12);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                switch (mType){
                    case FollowFragment.FOLLOWINGS:{
                        presenter.getFollowingsList(followingCursor,12);
                        break;
                    }
                    case FollowFragment.Fans:{
                        presenter.getFansList(fanCursor,12);
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        total=getBinding().textviewFollowTotal;
        if(mType==FollowFragment.FOLLOWINGS)
            total.setVisibility(View.INVISIBLE);

        list=getBinding().recyclerviewFollowList;
        manager=new LinearLayoutManager(activity);
        list.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        switch (mType){
            case FollowFragment.FOLLOWINGS:{
                presenter.getFollowingsList(followingCursor,12);
                break;
            }
            case FollowFragment.Fans:{
                presenter.getFansList(fanCursor,12);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void showFollowingsList(Followings followings) {
        if(list.getAdapter()==null){
            list.setAdapter(new FollowingsRecycleViewAdapter(followings.getList()));
        }else{
            FollowingsRecycleViewAdapter adapter=(FollowingsRecycleViewAdapter)list.getAdapter();
            adapter.addData(followings.getList());
            refreshLayout.finishRefresh();
        }
        followingCursor=followings.getCursor();
    }

    @Override
    public void showFansList(Fans fans) {
        if(list.getAdapter()==null){
            list.setAdapter(new FansRecycleViewAdapter(fans.getList()));
        }else{
            FansRecycleViewAdapter adapter=(FansRecycleViewAdapter)list.getAdapter();
            adapter.addData(fans.getList());
            refreshLayout.finishRefresh();
        }
        fanCursor=fans.getCursor();
        total.setText("我的粉丝("+fans.getTotal()+"人)");
    }

    @Override
    public void loadFail(String msg) {
        if(list.getAdapter()!=null){
            refreshLayout.finishRefresh(false);
        }
        ToastUtil.showToast(msg);
    }
}

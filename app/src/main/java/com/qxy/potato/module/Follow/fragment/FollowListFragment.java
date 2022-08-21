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

    private boolean followingsHasMore;
    private boolean fansHasMore;

    private MMKV mmkv=MMKV.defaultMMKV();

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
        refreshLayout.setOnRefreshListener(refreshlayout -> {
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
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            switch (mType){
                case FollowFragment.FOLLOWINGS:{
                    if(followingsHasMore)
                        presenter.getFollowingsList(followingCursor,12);
                    else
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    break;
                }
                case FollowFragment.Fans:{
                    if(fansHasMore)
                        presenter.getFansList(fanCursor,12);
                    else
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    break;
                }
                default:
                    break;
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
        if(list.getAdapter()==null&&followings.getList()!=null){
            list.setAdapter(new FollowingsRecycleViewAdapter(followings.getList()));
            mmkv.encode(GlobalConstant.FOLLOWINGS_TOTAL,followings.getList().size());
        }else{
            FollowingsRecycleViewAdapter adapter=(FollowingsRecycleViewAdapter)list.getAdapter();
            adapter.addData(followings.getList());
            mmkv.encode(GlobalConstant.FOLLOWINGS_TOTAL,followings.getList().size()+mmkv.getInt(GlobalConstant.FOLLOWINGS_TOTAL,0));
        }
        if(refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
        if(refreshLayout.isLoading())
            refreshLayout.finishLoadMore();
        followingsHasMore=followings.isHas_more();
        followingCursor=followings.getCursor();
    }

    @Override
    public void showFansList(Fans fans) {
        if(list.getAdapter()==null&&fans.getList()!=null){
            list.setAdapter(new FansRecycleViewAdapter(fans.getList()));
        }else{
            FansRecycleViewAdapter adapter=(FansRecycleViewAdapter)list.getAdapter();
            adapter.addData(fans.getList());
        }
        if(refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
        if(refreshLayout.isLoading())
            refreshLayout.finishLoadMore();
        fansHasMore=fans.isHas_more();
        fanCursor=fans.getCursor();
        mmkv.encode(GlobalConstant.FANS_TOTAL,fans.getTotal());
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

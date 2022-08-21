package com.qxy.potato.module.videorank.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potato.R;
import com.qxy.potato.annotation.BindEventBus;
import com.qxy.potato.base.BaseEvent;
import com.qxy.potato.base.BaseFragment;
import com.qxy.potato.bean.VideoList;
import com.qxy.potato.bean.VideoVersion;
import com.qxy.potato.common.EventCode;
import com.qxy.potato.databinding.FragmentRankBackgroundBinding;
import com.qxy.potato.module.videolist.rank.MyItemDecoration;
import com.qxy.potato.module.videorank.adapter.VideoRVAdapter;
import com.qxy.potato.module.videorank.presenter.VideoRankPresenter;
import com.qxy.potato.module.videorank.view.IVideoRankView;
import com.qxy.potato.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 16:34
 * @description：VideoRank的碎片层
 * @modified By：
 * @version: 1.0
 */
@BindEventBus
public class VideoRankFragment extends BaseFragment<VideoRankPresenter,FragmentRankBackgroundBinding> implements IVideoRankView {

    private final int type;

    public VideoRankFragment(int type) {
        this.type = type;
    }

    /**
     * 创建 presenter
     *
     * @return presenter
     */
    @Override
    protected VideoRankPresenter createPresenter() {
        return new VideoRankPresenter(this);
    }

    /**
     * 初始化布局
     */
    @Override
    protected void initView() {
        getBinding().textviewRankTime.setOnClickListener(v-> presenter.getClientVersion());
        getBinding().textviewRankRule.setOnClickListener(v -> {
        });

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.getNowRank(type);
    }

    /**
     * 展示榜单
     *
     * @param videoList 传入的影视List
     */
    @Override
    public void showRankSuccess(VideoList videoList) {
        VideoRVAdapter rvAdapter = new VideoRVAdapter(R.layout.recyclerview_item_rank,videoList.getList());
        getBinding().recyclerview.setAdapter(rvAdapter);
        getBinding().recyclerview.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false));
        getBinding().recyclerview.addItemDecoration(new MyItemDecoration(getContext()));
        getBinding().textviewRankTime.setText(String.format("本周榜|更新于 %s", videoList.getActive_time()));
    }

    /**
     * 错误信息提示
     *
     * @param errorMsg 错误信息
     */
    @Override
    public void showRankFailed(String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(BaseEvent<VideoVersion.Version> event){
        if (event.getEventCode() == EventCode.SELECT_VERSION){
            presenter.getLastVersionRank(type,event.getData().getVersion());
            ToastUtil.showToast(String.valueOf(event.getData().getVersion()));
        }
    }
}

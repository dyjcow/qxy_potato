package com.qxy.potatos.module.videorank.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qxy.potatos.R;
import com.qxy.potatos.annotation.BindEventBus;
import com.qxy.potatos.base.BaseEvent;
import com.qxy.potatos.base.BaseFragment;
import com.qxy.potatos.bean.VideoList;
import com.qxy.potatos.bean.VideoVersion;
import com.qxy.potatos.common.EventCode;
import com.qxy.potatos.databinding.FragmentRankBackgroundBinding;

import com.qxy.potatos.module.videorank.Dialog.RankItemDialog;

import com.qxy.potatos.module.videorank.activity.RankActivity;
import com.qxy.potatos.module.videorank.adapter.MyItemDecoration;
import com.qxy.potatos.module.videorank.adapter.VideoRankAdapter;
import com.qxy.potatos.module.videorank.presenter.VideoRankPresenter;
import com.qxy.potatos.module.videorank.view.IVideoRankView;
import com.qxy.potatos.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 16:34
 * @description：VideoRank的碎片层
 * @modified By：
 * @version: 1.0
 */
@BindEventBus
public class VideoRankFragment extends BaseFragment<VideoRankPresenter, FragmentRankBackgroundBinding> implements IVideoRankView {

    private final int type;
    private VideoRankAdapter rvAdapter;
    private RankActivity activity;

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
        activity = (RankActivity) getActivity();
        if (activity != null){
            getBinding().textviewRankTime.setOnClickListener(v -> presenter.getClientVersion(type, activity.hand));
            getBinding().textviewRankRule.setOnClickListener(v -> {
            });
        }
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
    public void showRankSuccess(VideoList videoList, int version) {
        //修复多次添加ItemDecoration导致的偏移问题
        if(rvAdapter == null){
            rvAdapter = new VideoRankAdapter(R.layout.recyclerview_item_rank, videoList.getList());
            rvAdapter.setOnItemClickListener((adapter, view, position) -> {
                if (activity != null){
                    RankItemDialog itemDialog = new RankItemDialog(activity.hand,videoList.getList().get(position));
                    itemDialog.show(getActivity().getSupportFragmentManager(), "MyFullDialog");
                }
            });
            //由于此处父布局是NSV,所以无法获得真正的高度，那么加载动画也只在第一次有效
            rvAdapter.setAnimationFirstOnly(true);
            rvAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
            getBinding().recyclerview.setLayoutManager(new LinearLayoutManager(requireContext(),
                    RecyclerView.VERTICAL, false));
            getBinding().recyclerview.addItemDecoration(new MyItemDecoration(getContext()));
        }else {
            rvAdapter.setList(videoList.getList());
        }
        getBinding().recyclerview.setAdapter(rvAdapter);
        if (version == -1) {
            getBinding().textviewRankTime.setText(String.format("本周榜|更新于 %s", videoList.getActive_time()));
        } else {
            getBinding().textviewRankTime.setText(String.format(Locale.CHINESE, "%d期|更新于 %s", version, videoList.getActive_time()));
        }

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
    public void onMainActivityEvent(BaseEvent<VideoVersion.Version> event) {
        if (event.getEventCode() == EventCode.SELECT_VERSION && event.getData().getType() == type) {
            presenter.getLastVersionRank(type, event.getData().getVersion());
        }
    }
}

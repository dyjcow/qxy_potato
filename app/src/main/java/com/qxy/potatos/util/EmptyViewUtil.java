package com.qxy.potatos.util;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.qxy.potatos.R;

/**
 * @Author 许朋友爱玩
 * @Date 2020/8/6 15:09
 * @Github https://github.com/LoveLifeEveryday
 * @JueJin https://juejin.im/user/5e429bbc5188254967066d1b/posts
 * @Description 空数据和网络错误界面的工具类
 */

public class EmptyViewUtil {
    /**
     * 得到网络错误的界面
     *
     * @param recyclerView rv
     * @return 网络错误的界面
     */
    public static View getErrorView(RecyclerView recyclerView) {
        return getView(R.layout.error_view, recyclerView);
    }

    /**
     * 得到空数据界面
     *
     * @param recyclerView rv
     * @return 空数据界面
     */
    public static View getEmptyDataView(RecyclerView recyclerView) {
        return getView(R.layout.empty_view, recyclerView);
    }


    private static View getView(int layout, RecyclerView recyclerView) {
        return ActivityUtil.getCurrentActivity().getLayoutInflater().inflate(layout, recyclerView, false);
    }
}

package com.qxy.potato.module.home.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *@author :yinxiaolong
 *@describe : com.qxy.potato.module.home.adapter
 *@date :2022/8/18 17:58
 */
class HomeItemDecoration(
    private val gridSize: Int,
    private val spaceH: Int = 0,
    private val spaceV: Int = 0,
    private val edgeH: Int = 0 // 网格两边的间距
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        // 获取第几列
        val column = position % gridSize
        // 第几行
        val row: Int = position / gridSize
        if (row != 0) { // 设置top
            outRect.top = spaceV
        }

        // p为每个Item都需要减去的间距
        val p = (2 * edgeH + (gridSize - 1) * spaceH) * 1f / gridSize
        val left = edgeH + column * (spaceH - p)
        val right = p - left

        outRect.left = Math.round(left)
        outRect.right = Math.round(right)
    }

}
package com.qxy.potato.module.videorank.Dialog

import com.bumptech.glide.Glide
import com.qxy.potato.bean.VideoList
import com.qxy.potato.databinding.LayoutDialogItemBinding
import java.text.DecimalFormat

/**
 * @author     ：Dyj
 * @date       ：Created in 2022/8/22 9:09
 * @description：榜单详情页
 * @modified By：
 * @version:     1.0
 */
class RankItemDialog(video : VideoList.Video) : MyFullDialog<LayoutDialogItemBinding>() {
    private val video : VideoList.Video
    init {
        this.video = video
    }
    override fun initView() {
        binding.ivClose.setOnClickListener{
            dismiss()
        }
        Glide.with(this)
            .load(video.poster)
            .into(binding.imageViewIcon)
        binding.textViewName.text = video.name
        binding.textViewPopDegree.text = getNumber(video.hot)
        binding.textViewReleaseTime.text = video.release_date + " 上映"
        if (video.tags == null) {
            binding.textViewType.text = "[国语]"
        } else {
            binding.textViewType.text = video.tags.toString() + ""
        }
        binding.textViewScore.text = "暂无评分"
        binding.tvDiscussionHot.text = getNumber(video.discussion_hot)
        binding.tvInfluenceHot.text = getNumber(video.influence_hot)
        binding.tvSearchHot.text = getNumber(video.search_hot)
        binding.tvActorsText.text = video.actors.toString()
    }

    private fun getNumber(num: Int): String? {
        val n = num.toFloat() / 10000
        val decimalFormat = DecimalFormat(".00")
        return decimalFormat.format(n.toDouble()) + "万"
    }

}
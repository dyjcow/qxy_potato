package com.qxy.potatos.module.videorank.Dialog

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.qxy.potatos.R
import com.qxy.potatos.bean.VideoList
import com.qxy.potatos.databinding.LayoutDialogItemBinding
import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier
import java.text.DecimalFormat

/**
 * @author     ：Dyj
 * @date       ：Created in 2022/8/22 9:09
 * @description：榜单详情页
 * @modified By：
 * @version:     1.0
 */
class RankItemDialog(hand:Int,video: VideoList.Video) : MyFullDialog<LayoutDialogItemBinding>() {
    private val video: VideoList.Video
    private val hand:Int

    init {
        this.video = video
        this.hand = hand
    }

    override fun initView() {
        val ivClose: ImageView = if (hand == OperatingHandClassifier.labelLeft) binding.ivCloseLeft
        else binding.ivCloseRight
        ivClose.apply {
            visibility = View.VISIBLE
            setOnClickListener { dismiss() }
        }
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        Glide.with(this)
            .load(video.poster)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageViewIcon)
        binding.textViewName.text = video.name
        binding.textViewPopDegree.text = getNumber(video.hot)
        binding.textViewReleaseTime.text = video.release_date + " 上映"
        if (video.tags == null) {
            binding.textViewType.text = "[国语]"
        } else {
            binding.textViewType.text = video.tags.toString()
        }
        binding.textViewScore.text = "暂无评分"
        binding.tvDiscussionHot.text = "讨论热度: " + getNumber(video.discussion_hot)
        binding.tvSearchHot.text = "搜索热度: " + getNumber(video.search_hot)
        if (video.type == 3) {
            binding.tvInfluenceHot.visibility = View.INVISIBLE
            binding.tvActors.text = "导演"
            binding.tvActorsText.text = video.directors.toString()
        } else {
            binding.tvInfluenceHot.text = "影响力: " + getNumber(video.influence_hot)
            binding.tvActorsText.text = video.actors.toString()
        }

    }

    private fun getNumber(num: Int): String? {
        val n = num.toFloat() / 10000
        val decimalFormat = DecimalFormat(".00")
        return decimalFormat.format(n.toDouble()) + "万"
    }

}
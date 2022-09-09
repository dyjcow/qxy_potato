package com.qxy.potatos.module.videorank.adapter

import android.view.View
import com.dylanc.viewbinding.brvah.getBinding
import com.qxy.potatos.bean.VideoList
import com.bumptech.glide.request.RequestOptions
import com.qxy.potatos.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import com.qxy.potatos.util.ActivityUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qxy.potatos.databinding.RecyclerviewItemRankBinding
import java.text.DecimalFormat

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/21 20:42
 * @description：Rv适配器
 * @modified By：
 * @version: 1.0
 */
class VideoRankAdapter(layoutResId: Int, data: MutableList<VideoList.Video>?) :
    BaseQuickAdapter<VideoList.Video, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: VideoList.Video) {
//        val binding = holder.getBinding { view: View? ->
//            RecyclerviewItemRankBinding.bind(
//                view!!
//            )
//        }
        val binding = holder.getBinding(RecyclerviewItemRankBinding::bind)
        val options = RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        Glide.with(ActivityUtil.getCurrentActivity())
            .load(item.poster)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageViewIcon)
        binding.textViewName.text = item.name
        binding.textViewPopDegree.text = getNumber(item.hot)
        binding.textViewReleaseTime.text = (item.release_date?:"未定时间") + " 上映"
        binding.textViewType.text = item.tags?.toString()?.let { getTag(it) } ?:"国语"
        binding.textViewScore.text = "暂无评分"
        if (item.type == 1) {
            binding.buttonGetTicket.setOnClickListener { v: View ->
                Toast.makeText(
                    v.context,
                    "无法购票",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            binding.buttonGetTicket.visibility = View.GONE
        }
    }

    private fun getNumber(num: Int): String {
        val n = num.toFloat() / 10000
        val decimalFormat = DecimalFormat(".00")
        return decimalFormat.format(n.toDouble()) + "万"
    }

    private fun getTag(tag: String): String {
        val mTag = tag.substring(1, tag.length-1)
        return mTag.replace(",","/")
    }
}
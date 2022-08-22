package com.qxy.potato.module.videorank.Dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qxy.potato.R

abstract class MyFullDialog<VB : ViewBinding> : BottomSheetDialogFragment() {



    protected lateinit var binding: VB

    /**
     * setStyle 圆角效果
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogBg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        initView()
        return binding.root
    }

    abstract fun initView()

    /**
     * 设置默认全屏显示
     */
    override fun onStart() {
        super.onStart()
        //拿到系统的 bottom_sheet
        val view: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        //设置view高度
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        //获取behavior
        val behavior = BottomSheetBehavior.from(view)
        //设置弹出高度
        behavior.peekHeight = 3000
        //设置展开状态
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.dismiss()
        super.onCancel(dialog)
    }

    override fun dismiss() {

        super.dismiss()
    }
}
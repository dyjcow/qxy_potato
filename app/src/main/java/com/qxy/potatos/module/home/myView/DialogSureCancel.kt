package com.qxy.potatos.module.home.myView

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qxy.potatos.R
import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier
import com.qxy.potatos.util.ColorUtil
import com.tamsiree.rxkit.RxDataTool
import com.tamsiree.rxui.view.dialog.RxDialog

/**
 * @author     ：Dyj
 * @date       ：Created in 2022/9/7 11:22
 * @description：确定取消的弹窗
 * @modified By：
 * @version:     1.0
 */
open class DialogSureCancel  : RxDialog {
    lateinit var logoView: ImageView
        private set
    lateinit var contentView: TextView
        private set
    lateinit var leftView: TextView
        private set
    lateinit var rightView: TextView
        private set
    lateinit var titleView: TextView
        private set
    private var title: String = ""
    private var logoIcon = -1
    private val handLabel:Int

    constructor(context: Context?, themeResId: Int, handLabel: Int) : super(context!!, themeResId) {
        this.handLabel = handLabel
        initView()
    }

    constructor(
        context: Context?,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?,
        handLabel: Int
    ) : super(context!!, cancelable, cancelListener) {
        this.handLabel = handLabel
        initView()
    }

    constructor(context: Context?, handLabel: Int) : super(context!!) {
        this.handLabel = handLabel
        initView()
    }

    constructor(context: Activity?, handLabel: Int) : super(context!!) {
        this.handLabel = handLabel
        initView()
    }

    constructor(context: Context?, alpha: Float, gravity: Int, handLabel: Int) : super(context, alpha, gravity) {
        this.handLabel = handLabel
        initView()
    }

    fun setContent(content: String?) {
        contentView.text = content
    }

    fun setSure(strSure: String?) {
        if (handLabel == OperatingHandClassifier.labelLeft){
            leftView.text = strSure
        }else {
            rightView.text = strSure
        }
    }

    fun setCancel(strCancel: String?) {
        if (handLabel == OperatingHandClassifier.labelLeft){
            rightView.text = strCancel
        }else {
            leftView.text = strCancel
        }
    }

    fun setSureListener(sureListener: View.OnClickListener?) {
        if (handLabel == OperatingHandClassifier.labelLeft){
            leftView.setOnClickListener(sureListener)
        }else {
            rightView.setOnClickListener(sureListener)
        }
    }

    fun setCancelListener(cancelListener: View.OnClickListener?) {
        if (handLabel == OperatingHandClassifier.labelLeft){
            rightView.setOnClickListener(cancelListener)
        }else {
            leftView.setOnClickListener(cancelListener)
        }
    }

    private fun initView() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sure_cancel, null)
        logoView = dialogView.findViewById(R.id.iv_logo)
        leftView = dialogView.findViewById(R.id.tv_left)
        rightView = dialogView.findViewById(R.id.tv_right)
        contentView = dialogView.findViewById(R.id.tv_content)
        contentView.setTextIsSelectable(true)
        titleView = dialogView.findViewById(R.id.tv_title)
        if (RxDataTool.isNullString(title)) {
            titleView.visibility = View.GONE
        }
        if (logoIcon == -1) {
            logoView.visibility = View.GONE
        }
        setContentView(dialogView)
        if (handLabel == OperatingHandClassifier.labelRight){
            rightView.setTextColor(ColorUtil.getResourcesColor(context,R.color.blue_right))
            leftView.setTextColor(ColorUtil.BLACK)
        }
    }

    fun setLogo(LOGOIcon: Int) {
        logoIcon = LOGOIcon
        if (logoIcon == -1) {
            logoView.visibility = View.GONE
            return
        }
        logoView.visibility = View.VISIBLE
        logoView.setImageResource(logoIcon)
    }

    fun setTitle(titleStr: String) {
        title = titleStr
        if (RxDataTool.isNullString(title)) {
            titleView.visibility = View.GONE
            return
        }
        titleView.visibility = View.VISIBLE
        titleView.text = title
    }
}
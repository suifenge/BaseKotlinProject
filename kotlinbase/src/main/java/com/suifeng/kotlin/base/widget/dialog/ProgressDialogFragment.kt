package com.suifeng.kotlin.base.widget.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import com.suifeng.kotlin.base.R
import com.trello.rxlifecycle4.components.support.RxDialogFragment


/**
 * @author admin
 * @data 2018/4/25
 * @describe
 */
class ProgressDialogFragment : RxDialogFragment(){
    //root
    private lateinit var mInflate : View
    //dismiss回调
    private var dismissCallback: () -> Unit = {}
    // 布局是否是原始的
    private var isInitLayout = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mInflate = inflater.inflate(R.layout.layout_dialog_loading, container,true)
        isInitLayout = mInflate.findViewById<View>(R.id.net_tv_message) != null
        return mInflate
    }

    override fun onStart() {
        super.onStart()
        // 启动动画
        startAnimation()
    }

    override fun onStop() {
        super.onStop()
        //  停止动画
        if (isInitLayout){
            mInflate.findViewById<View>(R.id.net_iv_loading).clearAnimation()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback()
    }

    /** 销毁回调 */
    fun setDismissCallback(callback: () -> Unit){
        dismissCallback = callback
    }

    /** 旋转动画  */
    private fun startAnimation() {
        if (isInitLayout){
            val rotate = AnimationUtils.loadAnimation(activity, R.anim.rotate)
            mInflate.findViewById<View>(R.id.net_iv_loading).startAnimation(rotate)
        }
    }



}
package com.suifeng.kotlin.base.widget.dialog.password

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.suifeng.kotlin.base.R

/**
 * @说明: 密码键盘DiaLog
 */
class PayFragmentDialog : DialogFragment(), View.OnClickListener {

    private var psw_input: PayPwdView? = null
    private var inputCallBack: PayPwdView.InputCallBack? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        val dialog = Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 设置Content前设定
        dialog.setContentView(R.layout.fragment_dialog_pay)
        dialog.setCanceledOnTouchOutside(false) // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        val window = dialog.window
        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT // 宽度持平
        lp.gravity = Gravity.TOP
        window.attributes = lp

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        val bundle = arguments
        if (bundle != null) {
            val tv_content = dialog.findViewById<View>(R.id.tv_content) as TextView
            tv_content.text = bundle.getString(EXTRA_CONTENT)
            val tv_tit = dialog.findViewById<View>(R.id.tv_tit) as TextView
            tv_tit.text = bundle.getString(EXTRA_TIT)
        }

        psw_input = dialog.findViewById<View>(R.id.payPwdView) as PayPwdView
        val inputMethodView = dialog.findViewById<View>(R.id.inputMethodView) as PwdInputMethodView
        psw_input!!.setInputMethodView(inputMethodView)
        psw_input!!.setInputCallBack(inputCallBack!!)

        psw_input!!.tag = tag
        dialog.findViewById<View>(R.id.iv_close).setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> dismiss()
        }
    }

    /**
     * 设置输入回调
     *
     * @param inputCallBack
     */
    fun setPaySuccessCallBack(inputCallBack: PayPwdView.InputCallBack) {
        this.inputCallBack = inputCallBack
    }

    companion object {

        val EXTRA_TIT = "extra_tit"    //标题内容
        val EXTRA_CONTENT = "extra_content"    //提示框内容
    }

}

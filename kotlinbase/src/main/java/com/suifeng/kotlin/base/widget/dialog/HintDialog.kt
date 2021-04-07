package com.suifeng.kotlin.base.widget.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.suifeng.kotlin.base.R


/**
 * @author admin
 * @data 2018/4/25
 * @describe ios风格HintDialog
 */
class HintDialog : DialogFragment() {
    // rootview
    private lateinit var mInflate: View
    // 事件
    private var sureListener: (View) -> Unit = {}
    private var dismissListener: (View) -> Unit = {}

    companion object {
        /**
         * 创建构造方法
         */
        fun newInstance(message: String, title: String = "提示", sureText: String = "确定", cancelText: String? = "取消"): HintDialog {
            val dialog = HintDialog()
            val bundle = Bundle()
            bundle.putString("title",title)
            bundle.putString("message",message)
            bundle.putString("sureText",sureText)
            if (cancelText != null){
                bundle.putString("cancelText",cancelText)
            }
            dialog.arguments = bundle
            return dialog
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mInflate = inflater.inflate(R.layout.layout_dialog_hint, container, true)!!
        // 初始化View
        mInflate.findViewById<TextView>(R.id.dialog_tv_title).text = arguments!!.getString("title")
        mInflate.findViewById<TextView>(R.id.dialog_tv_message).text = arguments!!.getString("message")
        mInflate.findViewById<TextView>(R.id.dialog_tv_ensure).text = arguments!!.getString("sureText")
        val cancelText = arguments!!.getString("cancelText")
        if (cancelText != null && cancelText.isNotEmpty()){
            mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).text = cancelText
        }else{
            mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).visibility = View.GONE
            mInflate.findViewById<View>(R.id.dialog_btn_divider).visibility = View.GONE
        }
        // 初始化事件
        mInflate.findViewById<TextView>(R.id.dialog_tv_ensure).setOnClickListener {
            dismiss()
            sureListener(it)
        }
        // 取消
        mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).setOnClickListener {
            dismiss()
        }
        return mInflate
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // 销毁回调
        dismissListener(mInflate)
    }

    /**
     * 显示HintDialog 带事件回调
     */
    fun show(manager: FragmentManager, tag: String, sureListener: (View) -> Unit, dismissListener: (View) -> Unit = {}): HintDialog {
        super.show(manager, tag)
        this.sureListener = sureListener
        this.dismissListener = dismissListener
        return this
    }

}

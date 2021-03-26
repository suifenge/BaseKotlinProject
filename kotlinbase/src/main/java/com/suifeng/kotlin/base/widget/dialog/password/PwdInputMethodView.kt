package com.suifeng.kotlin.base.widget.dialog.password

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.suifeng.kotlin.base.R

/**
 * 输入键盘
 */
class PwdInputMethodView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs), View.OnClickListener {

    private var inputReceiver: InputReceiver? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_password_input, this)

        initView()
    }

    private fun initView() {
        findViewById<View>(R.id.btn_1).setOnClickListener(this)
        findViewById<View>(R.id.btn_2).setOnClickListener(this)
        findViewById<View>(R.id.btn_3).setOnClickListener(this)
        findViewById<View>(R.id.btn_4).setOnClickListener(this)
        findViewById<View>(R.id.btn_5).setOnClickListener(this)
        findViewById<View>(R.id.btn_6).setOnClickListener(this)
        findViewById<View>(R.id.btn_7).setOnClickListener(this)
        findViewById<View>(R.id.btn_8).setOnClickListener(this)
        findViewById<View>(R.id.btn_9).setOnClickListener(this)
        findViewById<View>(R.id.btn_0).setOnClickListener(this)
        findViewById<View>(R.id.btn_del).setOnClickListener(this)

        //        findViewById(R.id.layout_hide).setOnClickListener(new OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                setVisibility(GONE);
        //            }
        //        });
    }

    override fun onClick(v: View) {
        val num = v.tag as String
        this.inputReceiver!!.receive(num)
    }


    /**
     * 设置接收器
     *
     * @param receiver
     */
    fun setInputReceiver(receiver: InputReceiver) {
        this.inputReceiver = receiver
    }

    /**
     * 输入接收器
     */
    interface InputReceiver {
        fun receive(num: String)
    }
}

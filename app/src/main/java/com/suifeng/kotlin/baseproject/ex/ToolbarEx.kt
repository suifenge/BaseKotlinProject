package com.suifeng.kotlin.baseproject.ex

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.suifeng.kotlin.baseproject.R

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */

inline fun AppCompatActivity.setToolbarTitle(
        toolbar: Toolbar,
        title: String,
        isReturn: Boolean = false
) {
    toolbar.findViewById<TextView>(R.id.tv_actionbar_title).text = title
    if(isReturn) {
        toolbar.setNavigationIcon(R.mipmap.left_out)
        toolbar.setNavigationOnClickListener { finish() }
    }
}

inline fun Fragment.setToolbarTitle(
        toolbar: Toolbar,
        title: String
) {
    toolbar.findViewById<TextView>(R.id.tv_actionbar_title).text = title
}
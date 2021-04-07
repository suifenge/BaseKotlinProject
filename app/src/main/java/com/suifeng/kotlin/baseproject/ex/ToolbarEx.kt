package com.suifeng.kotlin.baseproject.ex

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.suifeng.kotlin.baseproject.R
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragment

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */

inline fun RxAppCompatActivity.setToolbarTitle(
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

inline fun RxFragment.setToolbarTitle(
        toolbar: Toolbar,
        title: String
) {
    toolbar.findViewById<TextView>(R.id.tv_actionbar_title).text = title
}
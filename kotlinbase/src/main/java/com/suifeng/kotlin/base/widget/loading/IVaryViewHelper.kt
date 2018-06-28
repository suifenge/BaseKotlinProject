package com.suifeng.kotlin.base.widget.loading

import android.content.Context
import android.view.View

/**
 * @author ljc
 * @data 2018/6/28
 * @describe
 */
interface IVaryViewHelper {

    fun getCurrentLayout(): View?

    fun restoreView()

    fun showLayout(view: View)

    fun inflate(layoutId: Int): View

    fun getContext() : Context

    fun getView(): View
}
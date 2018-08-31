package com.suifeng.kotlin.base.adapter

import android.content.Context
import android.databinding.ViewDataBinding

/**
 * @author ljc
 * @data 2018/8/31
 * @describe
 */
abstract class NewBaseBindingAdapter<T, B : ViewDataBinding>(
        context: Context,
        private val layoutID: Int,
        val list: ArrayList<T> = ArrayList(),
        // Item点击监听回调
        var itemClickListener: BaseBindingAdapter.OnItemClickListener<T>? = null,
        // layout 上 绑定的监听id
        vararg var clickIDs: Int
)
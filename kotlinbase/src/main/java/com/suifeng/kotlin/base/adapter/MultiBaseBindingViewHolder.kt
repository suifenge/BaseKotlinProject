package com.suifeng.kotlin.base.adapter

import android.databinding.ViewDataBinding
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author ljc
 * @data 2018/8/31
 * @describe
 */
class MultiBaseBindingViewHolder<T: ViewDataBinding>(view: View): BaseViewHolder(view) {
    lateinit var binding: T
}
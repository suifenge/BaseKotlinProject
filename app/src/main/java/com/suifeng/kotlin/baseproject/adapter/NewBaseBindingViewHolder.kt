package com.suifeng.kotlin.baseproject.adapter

import android.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author ljc
 * @data 2018/8/31
 * @describe
 */
class NewBaseBindingViewHolder<out T: ViewDataBinding>(val binding: T): BaseViewHolder(binding.root)
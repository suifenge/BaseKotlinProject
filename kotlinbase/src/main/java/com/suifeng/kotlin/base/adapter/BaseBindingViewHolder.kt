package com.suifeng.kotlin.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class BaseBindingViewHolder<out T: ViewDataBinding>(val binding: T): RecyclerView.ViewHolder(binding.root)
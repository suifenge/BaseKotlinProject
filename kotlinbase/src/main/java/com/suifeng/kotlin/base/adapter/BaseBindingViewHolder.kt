package com.suifeng.kotlin.base.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class BaseBindingViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)
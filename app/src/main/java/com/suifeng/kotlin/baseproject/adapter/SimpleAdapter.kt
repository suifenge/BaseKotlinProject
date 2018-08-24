package com.suifeng.kotlin.baseproject.adapter

import android.content.Context
import com.suifeng.kotlin.base.adapter.BaseBindingAdapter
import com.suifeng.kotlin.base.adapter.BaseBindingViewHolder
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.DataBean
import com.suifeng.kotlin.baseproject.databinding.LayoutSimpleItemBinding

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class SimpleAdapter(context: Context): BaseBindingAdapter<DataBean, LayoutSimpleItemBinding>(
        context, R.layout.layout_simple_item) {

    override fun convert(holder: BaseBindingViewHolder<LayoutSimpleItemBinding>, bean: DataBean, position: Int) {
        holder.binding.dataBean = bean
    }
}
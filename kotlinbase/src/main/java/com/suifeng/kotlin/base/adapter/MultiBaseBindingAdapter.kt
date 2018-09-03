package com.suifeng.kotlin.base.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * @author ljc
 * @data 2018/8/31
 * @describe 对BaseRecyclerViewAdapterHelper进行封装的多功能Adapter
 */
abstract class MultiBaseBindingAdapter<T, B : ViewDataBinding>(
        layoutID: Int,
        list: ArrayList<T> = ArrayList()
): BaseQuickAdapter<T, MultiBaseBindingViewHolder<B>>(layoutID, list) {

    override fun createBaseViewHolder(view: View): MultiBaseBindingViewHolder<B> {
        return MultiBaseBindingViewHolder(view)
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): MultiBaseBindingViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(mLayoutInflater, layoutResId, parent, false)
        val view = binding?.root ?: getItemView(layoutResId, parent)
        val holder = MultiBaseBindingViewHolder<B>(view)
        holder.binding = binding
        return holder
    }

    override fun convert(helper: MultiBaseBindingViewHolder<B>, item: T) {
        convert(helper.binding, item)
        helper.binding.executePendingBindings()
    }

    abstract fun convert(binding: B, item: T)
}
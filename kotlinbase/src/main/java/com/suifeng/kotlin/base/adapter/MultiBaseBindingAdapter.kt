package com.suifeng.kotlin.base.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * @author ljc
 * @data 2018/8/31
 * @describe 对BaseRecyclerViewAdapterHelper进行封装的多功能Adapter
 */
abstract class MultiBaseBindingAdapter<T, B : ViewDataBinding>(
        layoutID: Int,
        list: ArrayList<T> = ArrayList()
): BaseQuickAdapter<T, BaseDataBindingHolder<B>>(layoutID, list) {

    override fun onItemViewHolderCreated(viewHolder: BaseDataBindingHolder<B>, viewType: Int) {
        DataBindingUtil.bind<B>(viewHolder.itemView)
    }

}
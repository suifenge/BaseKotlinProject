package com.suifeng.kotlin.baseproject.adapter

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.suifeng.kotlin.base.adapter.MultiBaseBindingAdapter
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.News
import com.suifeng.kotlin.baseproject.databinding.LayoutNewsItemBinding

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
class NewsAdapter: MultiBaseBindingAdapter<News.Result, LayoutNewsItemBinding>(
        R.layout.layout_news_item
) {
    override fun convert(holder: BaseDataBindingHolder<LayoutNewsItemBinding>, item: News.Result) {
        holder.dataBinding?.news = item
    }

}
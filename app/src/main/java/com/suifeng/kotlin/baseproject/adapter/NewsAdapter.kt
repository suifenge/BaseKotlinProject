package com.suifeng.kotlin.baseproject.adapter

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.suifeng.kotlin.base.adapter.MultiBaseBindingAdapter
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.NewsBean
import com.suifeng.kotlin.baseproject.databinding.LayoutNewsItemBinding

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
class NewsAdapter: MultiBaseBindingAdapter<NewsBean.Data.DataBean, LayoutNewsItemBinding>(
        R.layout.layout_news_item
) {
    override fun convert(holder: BaseDataBindingHolder<LayoutNewsItemBinding>, item: NewsBean.Data.DataBean) {
        holder.dataBinding?.news = item
        holder.dataBinding?.rvImg?.layoutManager = GridLayoutManager(holder.itemView.context, 3)
        holder.dataBinding?.rvImg?.adapter = NewsPictureAdapter(item.picInfo)
    }

}
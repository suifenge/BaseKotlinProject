package com.suifeng.kotlin.baseproject.adapter

import android.support.v7.widget.GridLayoutManager
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
    override fun convert(binding: LayoutNewsItemBinding, item: NewsBean.Data.DataBean) {
        binding.news = item

        binding.rvImg.layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.rvImg.adapter = NewsPictureAdapter(item.picInfo)
    }

}
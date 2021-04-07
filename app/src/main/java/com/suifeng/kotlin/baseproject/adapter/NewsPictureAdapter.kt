package com.suifeng.kotlin.baseproject.adapter

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.suifeng.kotlin.base.adapter.MultiBaseBindingAdapter
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.NewsBean
import com.suifeng.kotlin.baseproject.databinding.LayoutNewsPicItemBinding

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
class NewsPictureAdapter(data: ArrayList<NewsBean.Data.DataBean.PicInfo>): MultiBaseBindingAdapter<NewsBean.Data.DataBean.PicInfo, LayoutNewsPicItemBinding>(
        R.layout.layout_news_pic_item,
        data
) {
    override fun convert(holder: BaseDataBindingHolder<LayoutNewsPicItemBinding>, item: NewsBean.Data.DataBean.PicInfo) {
        holder.dataBinding?.picture = item
    }
}
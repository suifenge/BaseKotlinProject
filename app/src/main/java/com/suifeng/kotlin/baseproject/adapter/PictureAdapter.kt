package com.suifeng.kotlin.baseproject.adapter

import android.content.Context
import com.suifeng.kotlin.base.adapter.BaseBindingAdapter
import com.suifeng.kotlin.base.adapter.BaseBindingViewHolder
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.databinding.LayoutPictureItemBinding

/**
 * @author ljc
 * @data 2018/8/14
 * @describe
 */
class PictureAdapter (context: Context): BaseBindingAdapter<PictureBean.Data, LayoutPictureItemBinding>(
        context, R.layout.layout_picture_item) {
    override fun convert(holder: BaseBindingViewHolder<LayoutPictureItemBinding>, bean: PictureBean.Data, position: Int) {
        holder.binding.picture = bean
    }

}
package com.suifeng.kotlin.baseproject.adapter

import android.content.Context
import com.suifeng.kotlin.base.adapter.BaseBindingAdapter
import com.suifeng.kotlin.baseproject.BR
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.PictureBean

/**
 * @author ljc
 * @data 2018/8/14
 * @describe
 */
class PictureAdapter (context: Context): BaseBindingAdapter<PictureBean.Data>(
        context, BR.picture, R.layout.layout_picture_item)
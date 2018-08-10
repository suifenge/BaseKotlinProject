package com.suifeng.kotlin.baseproject.adapter

import android.content.Context
import com.suifeng.kotlin.base.adapter.BaseBindingAdapter
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.bean.DataBean

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class SimpleAdapter(context: Context, variableId: Int, val data:ArrayList<DataBean>): BaseBindingAdapter<DataBean>(
        context, variableId, R.layout.layout_simple_item, data)
package com.suifeng.kotlin.base.binding.viewadapter.imageview

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */

@BindingAdapter(value = ["url", "placeHolderRes", "errorRes"])
public fun setImageUrl(imageView: ImageView, url: String?, placeHolderRes: Int, errorRes: Int) {
    if(!TextUtils.isEmpty(url)) {
        val requestOptions = RequestOptions()
        if(placeHolderRes != 0) {
            requestOptions.placeholder(placeHolderRes)
        }
        if(errorRes != 0 ) {
            requestOptions.error(errorRes)
        }
        Glide.with(imageView.context)
                .load(url)
                .apply(requestOptions)
                .into(imageView)
    }
}
package com.suifeng.kotlin.baseproject.utils

import android.graphics.drawable.Drawable
import com.suifeng.kotlin.baseproject.CustomApplication

/**
 * @author ljc
 * @data 2018/8/24
 * @describe
 */
object ResourceUtils {

    @JvmStatic
    fun getDrawable(resId: Int): Drawable {
        val application = CustomApplication.instance
        return application.resources.getDrawable(resId)
    }
}
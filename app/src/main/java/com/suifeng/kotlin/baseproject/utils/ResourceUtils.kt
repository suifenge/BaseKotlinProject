package com.suifeng.kotlin.baseproject.utils

import android.graphics.drawable.Drawable
import com.suifeng.kotlin.base.BaseApplication

/**
 * @author ljc
 * @data 2018/8/24
 * @describe
 */
object ResourceUtils {

    @JvmStatic
    fun getDrawable(resId: Int): Drawable {
        val application = BaseApplication.instance
        return application.resources.getDrawable(resId)
    }
}
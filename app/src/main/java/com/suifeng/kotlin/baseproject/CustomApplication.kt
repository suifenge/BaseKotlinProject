package com.suifeng.kotlin.baseproject

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.baseproject.ex.initThird

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class CustomApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApp()
    }

    companion object {
        @JvmStatic lateinit var instance: Application
    }

    private fun initApp() {
        ARouter.init(this)
        initThird()
    }
}
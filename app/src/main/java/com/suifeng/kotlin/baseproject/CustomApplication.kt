package com.suifeng.kotlin.baseproject

import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.base.BaseApplication
import com.suifeng.kotlin.baseproject.ex.initThird

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class CustomApplication: BaseApplication() {

    override fun initApp() {
        ARouter.init(this)
        initThird()
    }
}
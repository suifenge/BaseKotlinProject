package com.suifeng.kotlin.baseproject

import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.baseproject.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class CustomApplication: DaggerApplication() {
    //可以直接继承Application，但是必须实现相应接口（如：HasActivityInjector、HasSupportFragmentInjector等等）

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ARouter.init(this)
    }

    companion object {
        @JvmStatic lateinit var instance: CustomApplication
            private set
    }

}
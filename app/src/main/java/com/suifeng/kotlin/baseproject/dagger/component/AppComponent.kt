package com.suifeng.kotlin.baseproject.dagger.component

import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.dagger.module.ViewModelFactoryModule
import com.suifeng.kotlin.baseproject.dagger.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/9
 * @describe
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ViewModelModule::class, ViewModelFactoryModule::class])
interface AppComponent: AndroidInjector<CustomApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CustomApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(application: CustomApplication)

}
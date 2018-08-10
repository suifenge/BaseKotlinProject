package com.suifeng.kotlin.baseproject.dagger.module

import android.arch.lifecycle.ViewModelProvider
import com.suifeng.kotlin.base.ui.vm.ViewModelFactory
import com.suifeng.kotlin.baseproject.DemoActivity
import com.suifeng.kotlin.baseproject.MainActivity
import com.suifeng.kotlin.baseproject.RecyclerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author ljc
 * @data 2018/8/9
 * @describe
 */

@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeDemoActivity(): DemoActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeRecyclerActivity(): RecyclerActivity
}
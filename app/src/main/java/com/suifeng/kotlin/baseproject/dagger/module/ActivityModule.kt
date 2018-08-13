package com.suifeng.kotlin.baseproject.dagger.module

import com.suifeng.kotlin.baseproject.activity.DemoActivity
import com.suifeng.kotlin.baseproject.activity.MainActivity
import com.suifeng.kotlin.baseproject.activity.NetActivity
import com.suifeng.kotlin.baseproject.activity.RecyclerActivity
import com.suifeng.kotlin.baseproject.dagger.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author ljc
 * @data 2018/8/9
 * @describe
 */

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeDemoActivity(): DemoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeRecyclerActivity(): RecyclerActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeNetActivity(): NetActivity
}
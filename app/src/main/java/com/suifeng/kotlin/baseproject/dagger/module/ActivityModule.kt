package com.suifeng.kotlin.baseproject.dagger.module

import com.suifeng.kotlin.baseproject.activity.*
import com.suifeng.kotlin.baseproject.dagger.scopes.ActivityScope
import com.suifeng.kotlin.baseproject.dagger.scopes.FragmentScope
import com.suifeng.kotlin.baseproject.fragment.DemoFragment
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

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeFragmentActivity(): FragmentActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    internal abstract fun contributeMultiActivity(): MultiActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class, FragmentModule::class])
    internal abstract fun contributeFragment(): DemoFragment
}
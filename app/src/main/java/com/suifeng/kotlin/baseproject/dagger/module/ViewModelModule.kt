package com.suifeng.kotlin.baseproject.dagger.module

import android.arch.lifecycle.ViewModel
import com.suifeng.kotlin.baseproject.dagger.scopes.ViewModelKey
import com.suifeng.kotlin.baseproject.vm.DemoViewModel
import com.suifeng.kotlin.baseproject.vm.LoginViewModel
import com.suifeng.kotlin.baseproject.vm.RecyclerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author ljc
 * @data 2018/8/9
 * @describe
 */
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    abstract fun bindDemoViewModel(viewModel: DemoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecyclerViewModel::class)
    abstract fun bindRecyclerViewViewModel(viewModel: RecyclerViewModel): ViewModel

}
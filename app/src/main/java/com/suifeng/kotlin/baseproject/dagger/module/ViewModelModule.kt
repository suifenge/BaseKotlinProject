package com.suifeng.kotlin.baseproject.dagger.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.suifeng.kotlin.base.mvvm.vm.ViewModelFactory
import com.suifeng.kotlin.baseproject.dagger.scopes.ViewModelKey
import com.suifeng.kotlin.baseproject.vm.*
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
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

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
    abstract fun bindRecyclerViewModel(viewModel: RecyclerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NetViewModel::class)
    abstract fun bindNetViewModel(viewModel: NetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: FragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragDemoViewModel::class)
    abstract fun bindFragDemoViewModel(viewModel: FragDemoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MultiViewModel::class)
    abstract fun bindMultiViewModel(viewModel: MultiViewModel): ViewModel

}
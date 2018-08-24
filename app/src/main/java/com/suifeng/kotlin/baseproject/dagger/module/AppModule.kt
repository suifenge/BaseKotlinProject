package com.suifeng.kotlin.baseproject.dagger.module

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
import com.suifeng.kotlin.base.net.common.RetrofitClient
import com.suifeng.kotlin.baseproject.consts.Constants
import com.suifeng.kotlin.baseproject.data.api.ICommonApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Module
internal object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideHttpService(): ICommonApi {
        return RetrofitClient.Builder(ICommonApi::class.java, true, Constants.BASE_URL, headers = {
            val hashMap = HashMap<String, String>()
            hashMap
        }).create()
    }

    @Singleton
    @Provides
    @JvmStatic
            /**
             * 共享的ViewModelProvider，提供全局的ViewModelStore
             * @param factory factory的创建需要有Map<Class,ViewModel>的支持，所以需要IntoMap来提供具体的ViewModel
             * @see AppViewModelModule
             */
    fun getShareViewModelProvider(factory: ViewModelProvider.Factory): ViewModelProvider {
        val viewModelStore = ViewModelStore()
        return ViewModelProvider(viewModelStore, factory)
    }
}
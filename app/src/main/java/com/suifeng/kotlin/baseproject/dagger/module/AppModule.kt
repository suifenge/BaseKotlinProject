package com.suifeng.kotlin.baseproject.dagger.module

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
}
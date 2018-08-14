package com.suifeng.kotlin.baseproject.dagger.module

import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.adapter.PictureAdapter
import dagger.Module
import dagger.Provides

/**
 * @author ljc
 * @data 2018/8/14
 * @describe
 */

@Module
class FragmentModule {

    @Provides
    fun providePictureAdapter(context: CustomApplication): PictureAdapter {
        return PictureAdapter(context)
    }
}
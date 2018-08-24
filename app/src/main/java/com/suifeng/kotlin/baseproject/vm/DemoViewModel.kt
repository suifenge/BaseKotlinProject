package com.suifeng.kotlin.baseproject.vm

import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.CustomApplication
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/8
 * @describe
 */

@Singleton
class DemoViewModel @Inject constructor(application: CustomApplication): BaseViewModel(application)
package com.suifeng.kotlin.baseproject.ex

import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.exception.ApiException
import com.suifeng.kotlin.baseproject.BuildConfig

/**
 * @author ljc
 * @data 2018/8/24
 * @describe
 */
public inline fun BaseViewModel.responseError(e: Throwable, isShowErrorMessage: Boolean = BuildConfig.DEBUG) {
    if(e is ApiException) {
        error.call(e.statusCode, e.displayMessage)
    } else {
        if(isShowErrorMessage) {
            e.message?.let {
                error.call(503, it)
            }
        }
    }
}
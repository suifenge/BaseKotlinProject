package com.suifeng.kotlin.base.mvvm.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ProgressLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application){
    val progress = ProgressLiveData()
    val error = ErrorLiveData()
    val toast = ToastLiveData()
}
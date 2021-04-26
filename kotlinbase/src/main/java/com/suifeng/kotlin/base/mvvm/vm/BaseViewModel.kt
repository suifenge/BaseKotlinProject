package com.suifeng.kotlin.base.mvvm.vm

import androidx.lifecycle.ViewModel
import com.suifeng.kotlin.base.mvvm.livedata.ActivityLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ProgressLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel() : ViewModel(){

    val progress = ProgressLiveData()
    val error = ErrorLiveData()
    val toast = ToastLiveData()
    val activity = ActivityLiveData()

    override fun onCleared() {
        super.onCleared()
    }
}
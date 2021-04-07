package com.suifeng.kotlin.base.mvvm.vm

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ProgressLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData

/**
 * Created by Hzz on 2018/8/29
 */
interface IObserverProvider {
    fun providerProgressObserver(owner: FragmentActivity): Observer<ProgressLiveData.Progress>?
    fun providerToastObserver(owner:FragmentActivity):Observer<ToastLiveData.Toast>?
    fun providerErrorObserver(owner:FragmentActivity):Observer<ErrorLiveData.Error>?
}
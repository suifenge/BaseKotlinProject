package com.suifeng.kotlin.base.mvvm.observer

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.suifeng.kotlin.base.extension.IToast
import com.suifeng.kotlin.base.extension.toast
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData.Error

class DefaultErrorObserver(private val owner: FragmentActivity) : Observer<Error> {
    override fun onChanged(it: Error?) {
        owner.toast(it?.message ?: "", IToast.ERROR)
    }
}
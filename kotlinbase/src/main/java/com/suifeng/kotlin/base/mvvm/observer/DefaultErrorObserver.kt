package com.suifeng.kotlin.base.mvvm.observer

import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import com.ssf.framework.widget.ex.IToast
import com.suifeng.kotlin.base.extension.toast
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData.Error

class DefaultErrorObserver(val owner: FragmentActivity) : Observer<Error> {
    override fun onChanged(it: Error?) {
        owner.toast(it?.message ?: "", IToast.ERROR)
    }
}
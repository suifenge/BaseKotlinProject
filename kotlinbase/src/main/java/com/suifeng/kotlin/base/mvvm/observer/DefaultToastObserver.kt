package com.suifeng.kotlin.base.mvvm.observer

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.suifeng.kotlin.base.extension.IToast
import com.suifeng.kotlin.base.extension.toast
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData

class DefaultToastObserver(private val owner: FragmentActivity) : Observer<ToastLiveData.Toast> {

    override fun onChanged(it: ToastLiveData.Toast?) {
        owner.toast(it?.message ?: "", it?.type ?: IToast.NORMAL)
    }

}
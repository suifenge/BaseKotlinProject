package com.suifeng.kotlin.base.mvvm.observer

import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import com.ssf.framework.widget.ex.IToast
import com.suifeng.kotlin.base.extension.toast
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData

class DefaultToastObserver(val owner: FragmentActivity) : Observer<ToastLiveData.Toast> {

    override fun onChanged(it: ToastLiveData.Toast?) {
        owner.toast(it?.message ?: "", it?.type ?: IToast.NORMAL)
    }

}
package com.suifeng.kotlin.base.mvvm.livedata

import com.ssf.framework.widget.ex.IToast
import com.suifeng.kotlin.base.mvvm.livedata.event.EventLiveData

class ToastLiveData : EventLiveData<ToastLiveData.Toast>() {

    fun show(message: String, type: IToast = IToast.NORMAL) {
        postEvent(Toast(type, message))
    }

    class Toast(val type: IToast, val message: String)
}
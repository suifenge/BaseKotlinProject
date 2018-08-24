package com.suifeng.kotlin.base.mvvm.livedata

import com.suifeng.kotlin.base.mvvm.livedata.event.EventLiveData

class ProgressLiveData : EventLiveData<ProgressLiveData.Progress>() {

    fun show(message: String = "") {
        postEvent(Progress(true, message))
    }

    fun hide() {
        postEvent(Progress(false))
    }

    class Progress(val show: Boolean, val message: String = "",val cancelable:Boolean = false)
}
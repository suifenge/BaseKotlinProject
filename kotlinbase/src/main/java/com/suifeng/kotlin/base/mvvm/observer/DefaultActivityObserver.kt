package com.suifeng.kotlin.base.mvvm.observer

import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import com.suifeng.kotlin.base.mvvm.livedata.ActivityLiveData


class DefaultActivityObserver(val owner: FragmentActivity) : Observer<ActivityLiveData.Result> {

    override fun onChanged(it: ActivityLiveData.Result?) {
        it?.let {
            when (it.cmd) {
                ActivityLiveData.CMD_FINISH -> {
                    owner.finish()
                }
                ActivityLiveData.CMD_FINISH_RESULT -> {
                    owner.setResult(it.resutCode, it.data)
                    owner.finish()
                }
            }
        }
    }

}
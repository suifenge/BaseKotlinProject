package com.suifeng.kotlin.base.mvvm.observer

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.suifeng.kotlin.base.mvvm.livedata.ActivityLiveData


class DefaultActivityObserver(private val owner: FragmentActivity) : Observer<ActivityLiveData.Result> {

    override fun onChanged(it: ActivityLiveData.Result?) {
        it?.let {
            when (it.cmd) {
                ActivityLiveData.CMD_FINISH -> {
                    owner.finish()
                }
                ActivityLiveData.CMD_FINISH_RESULT -> {
                    owner.setResult(it.resultCode, it.data)
                    owner.finish()
                }
            }
        }
    }

}
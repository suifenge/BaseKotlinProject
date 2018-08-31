package com.suifeng.kotlin.baseproject.event

import com.suifeng.kotlin.base.mvvm.livedata.event.EventLiveData

/**
 * @author ljc
 * @data 2018/8/31
 * @describe
 */
class RefreshLiveData: EventLiveData<RefreshLiveData.RefreshResult>() {

    companion object {
        const val REFRESH_SUCCESS = 0
        const val REFRESH_FAILED = 1
        const val LOAD_MORE_SUCCESS = 2
        const val LOAD_MORE_FAILED = 3
    }

    fun result(state: Int) {
        postEvent(RefreshResult(state))
    }

    class RefreshResult(val state: Int)

}
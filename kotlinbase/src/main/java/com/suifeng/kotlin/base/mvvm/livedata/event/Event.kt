package com.suifeng.kotlin.base.mvvm.livedata.event

class Event<T> (
        val data:T,
        var hasBeenHandled:Boolean=false
) {

    /**
     * 返回数据内容
     */
    fun getDataIfNotHandled():T?{
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            data
        }
    }
}
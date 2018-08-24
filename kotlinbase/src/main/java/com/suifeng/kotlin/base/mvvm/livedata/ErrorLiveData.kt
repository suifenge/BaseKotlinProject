package com.suifeng.kotlin.base.mvvm.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData.Error
import com.suifeng.kotlin.base.mvvm.livedata.event.EventLiveData

/**
 * 错误处理
 */
class ErrorLiveData : EventLiveData<Error>() {
    companion object {
        const val NO_TARGET_ACTION = -1//没有指定目标action，一般统一处理的可以不做指定
    }

    private var defaultObserver: Observer<Error>? = null
    private var haveSetObserver = false//标识是否有主动设置Observer

    /**
     * 执行此方法来通知观察者有错误需要处理
     */
    fun call(code: Int, message: String, actionId: Int = NO_TARGET_ACTION) {
        postEvent(Error(code, message, actionId))
    }

    /**
     * 设置默认的错误观察者，在观察者处理完后有条件的触发此观察者
     */
    fun defaultObserver(observer: Observer<Error>) {
        defaultObserver = observer
        //注册一个默认的监听,用来检查是否有主动添加过Observer，如果没有就直接执行默认的observer
        super.observeEventForeverUnCheckHandled(Observer {
            if (!haveSetObserver) {
                defaultObserver?.onChanged(it)
            }
        })
    }

    //没有Observer监听后会回调此方法
    override fun onInactive() {
        super.onInactive()
        haveSetObserver = false //重新置为没有监听者
    }

    /**
     * 添加活动状态下的错误观察对象
     *
     * 事件只发送会有一个消费者,重复添加观察对象只有第一个会执行
     * @param owner 事件变动者
     * @param observer 带拦截控制的观察者,返回true表示拦截
     */
    fun observeError(owner: LifecycleOwner, observer: ObserverError) {
        haveSetObserver = true
        super.observeEvent(owner, Observer {
            if (!observer.onChangedNeedIntercept(it!!)) {//如果观察者没有返回true拦截，就执行默认的观察者
                defaultObserver?.onChanged(it)
            }
        })
    }

    /**
     * 添加任何状态下的错误观察对象
     *
     * 事件只发送会有一个消费者,重复添加观察对象只有第一个会执行
     */
    fun observeForeverError(observer: ObserverError) {
        haveSetObserver = true
        super.observeEventForever(Observer {
            if (!observer.onChangedNeedIntercept(it!!)) {//如果观察者没有返回true拦截，就执行默认的观察者
                defaultObserver?.onChanged(it)
            }
        })
    }


    class Error(
            val code: Int,//错误码
            val message: String,//错误消息
            val actionId: Int//用来标示执行的任务，非必填
    )
}
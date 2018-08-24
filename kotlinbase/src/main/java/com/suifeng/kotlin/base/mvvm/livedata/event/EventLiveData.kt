package com.suifeng.kotlin.base.mvvm.livedata.event

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer


/**
 * 用来发送事件的LiveData，不管什么情况始终只发送一次事件。
 * 适用于操作非layout上的UI
 */
open class EventLiveData<T>: SubscribeLiveData<Event<T>>() {

    fun postEvent(value:T){
        super.postValue(Event(value))
    }

    /**
     * 不能直接创建监听，需要通过ObserverEvent方法
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<Event<T>>) {
        throw IllegalAccessException("can not access this function,please use ObserverEvent")
    }

    override fun observeForever(observer: Observer<Event<T>>) {
        throw IllegalAccessException("can not access this function,please use observeEventForever")
    }

    /**
     * onActive状态下,检查消费，已消费不发送事件,意味着只要有一个Observer消费，其它observer就收不到消息
     */
    fun observeEvent(owner: LifecycleOwner,observer: Observer<T>):Observer<Event<T>>{
        val observerWrapper = EventObserverWrapper(observer)
        super.observe(owner,observerWrapper)
        return observerWrapper
    }

    /**
     * 所有状态下,检查消费，已消费不发送事件
     */
    fun observeEventForever(observer: Observer<T>):Observer<Event<T>>{
        val observerWrapper = EventObserverWrapper(observer)
        super.observeForever(observerWrapper)
        return observerWrapper
    }

    /**
     * onActive状态下,不检查消费始终发送事件
     */
    protected open fun observeEventUnCheckHandled(owner: LifecycleOwner,observer: Observer<T>):Observer<Event<T>>{
        val observerWrapper = EventObserverWrapper(observer,checkHandled = false)
        super.observe(owner,observerWrapper)
        return observerWrapper
    }

    /**
     * 所有状态下,不检查消费始终发送事件
     */
    protected open fun observeEventForeverUnCheckHandled(observer: Observer<T>):Observer<Event<T>>{
        val observerWrapper = EventObserverWrapper(observer,checkHandled = false)
        super.observeForever(observerWrapper)
        return observerWrapper
    }

    class EventObserverWrapper<T>(
            private val observer: Observer<T>,
            private val checkHandled: Boolean = true
    ) :Observer<Event<T>>{
        override fun onChanged(t: Event<T>?) {
            if(checkHandled) {
                //检查消费
                t?.getDataIfNotHandled()?.let {
                    observer.onChanged(it)
                }
            }else{
                //始终发送事件
                observer.onChanged(t?.data)
            }
        }

    }
}
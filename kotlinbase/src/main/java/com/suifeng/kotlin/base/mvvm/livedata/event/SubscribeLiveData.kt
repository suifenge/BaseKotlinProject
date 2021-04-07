package com.suifeng.kotlin.base.mvvm.livedata.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 针对初次订阅时，总时会收到订阅之前的事件问题修改
 * 原理是反射修改ObserverWrapper的mLastVersion值
 *
 * @see LiveData.considerNotify ->触发Observer.onChanged的方法
 * 此方法在mLastVersion小于LiveData.mVersion时总时会发送事件，而每次订阅时mLastVersion总是为-1，
 * 也就每次订阅时会发送一个事件
 *
 * @see LiveData.observe ->添加ObserverWrapper的方法
 * 解决此问题的办法就是在订阅时把订阅的ObserverWrapper.mLastVersion修改为等于mVersion
 *
 * 参考博客(基于LiveData实现LiveDataBus)
 * @link {http://www.cnblogs.com/meituantech/p/9376449.html}
 */
open class SubscribeLiveData<T>: LiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        //liveData.mObservers
        val clzLiveData = LiveData::class.java
        val fieldObservers = clzLiveData.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val objObservers = fieldObservers.get(this)
        //获取mObservers.get()方法
        val clazObservers = objObservers.javaClass
        val methodGet = clazObservers.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true
        //执行mObservers.get(observer),返回LifecycleBoundObserver对象(继承自ObserverWrapper)
        val objWrapperEntry = methodGet.invoke(objObservers, observer)
        var objWrapper:Any? =null
        if(objWrapperEntry is Map.Entry<*,*>){
            objWrapper = objWrapperEntry.value
        }

        //获取弗雷ObserverWrapper中的成员mLastVersion和LiveData的成员mVersion
        //将mVersion的值赋予给mLastVersion
        //打完手工！
        objWrapper?.let {
            val clzObserverWrapper = objWrapper!!.javaClass.superclass
            val fieldLastVersion = clzObserverWrapper.getDeclaredField("mLastVersion")
            val clzLiveData = LiveData::class.java
            val fieldVersion = clzLiveData.getDeclaredField("mVersion")
            fieldLastVersion.isAccessible = true
            fieldVersion.isAccessible = true
            val version = fieldVersion.get(this)
            fieldLastVersion.set(objWrapper,version)
            //end
            clzObserverWrapper
        }

    }

    //observeForever源码分析不能按照observe方式去处理，处理方法时查询调用栈，如果是observeForever发起的就不执行
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(ObserverWrapper(observer))
    }


    class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        override fun onChanged(t: T?) {
            if(isCallObserve()){
                return
            }
            observer.onChanged(t)
        }

        /**
         * 查看调用栈，有点高逼格 Orz
         */
        private fun isCallObserve():Boolean{
            val stackTrace = Thread.currentThread().stackTrace
            if (stackTrace != null && stackTrace.isNotEmpty()) {
                for (element in stackTrace) {
                    if ("android.arch.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                        return true
                    }
                }
            }
            return false
        }
    }
}
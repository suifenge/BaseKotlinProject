package com.suifeng.kotlin.base.mvvm.vm

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.suifeng.kotlin.base.mvvm.observer.DefaultActivityObserver
import com.suifeng.kotlin.base.mvvm.observer.DefaultErrorObserver
import com.suifeng.kotlin.base.mvvm.observer.DefaultProgressObserver
import com.suifeng.kotlin.base.mvvm.observer.DefaultToastObserver
import java.lang.RuntimeException

/**
 * ViewModel提供者
 *
 * ViewModelStore有两个
 * Activity级的ViewModelStore，存非AppViewModel之外所有
 * Application级的ViewModelStore,只能存AppViewModel
 */
class SuperViewModelProvider(private val lifecycleOwner: LifecycleOwner, factory: Factory,
                             private val appViewModelProvider: ViewModelProvider? = null) : ViewModelProvider(
        if (lifecycleOwner is FragmentActivity) {
            lifecycleOwner
        } else if (lifecycleOwner is Fragment && lifecycleOwner.activity != null) {
            lifecycleOwner.activity!!
        } else {//activity is null
            throw RuntimeException("Fragment中创建ViewModel必须在onAttach之后")
        }, factory) {

    private lateinit var activity: FragmentActivity
    //各个工程自定义风格的处理者
    private var mCustomObserverProvider: IObserverProvider? = null

    init {
        if (lifecycleOwner is FragmentActivity) {
            activity = lifecycleOwner
        } else if (lifecycleOwner is Fragment) {
            activity = lifecycleOwner.activity!!
        }
    }

    //置为全局是为了同个ViewModelStore作用于相同的对象
    private val defaultProgressObserver by lazy { DefaultProgressObserver(activity) }
    private val defaultToastObserver by lazy { DefaultToastObserver(activity) }
    private val defaultErrorObserver by lazy { DefaultErrorObserver(activity) }
    private val defaultActivityObserver by lazy { DefaultActivityObserver(activity) }

    override fun <T : ViewModel?> get(key: String, modelClass: Class<T>): T {
        //需要获取的类是AppViewModel时,如果有AppViewModelProvider存取到App#ViewModelProvider，没有就存到Activity#ViewModelProvider里
        appViewModelProvider?.let {
            if (AppViewModel::class.java.isAssignableFrom(modelClass)) {
                //get viewModel from ApplicationViewModelStore
                return appViewModelProvider.get(modelClass)
            }
        }

        //get viewModel from ownerViewModelStore
        val vm = super.get(key, modelClass)//从ViewModelStore中取出ViewModel
        if (vm is BaseViewModel) {
            //如果是BaseViewModel处理一些默认监听，因为是Activity内共享的，监听时要考虑多个VM可能会造成的冲突
            //如果自定义的Observer提供者中有提供自己的处理就使用提供者的，否则就用默认的
            vm.progress.observeEvent(lifecycleOwner, mCustomObserverProvider?.providerProgressObserver(activity)
                    ?: defaultProgressObserver)
            vm.toast.observeEvent(lifecycleOwner, mCustomObserverProvider?.providerToastObserver(activity)
                    ?: defaultToastObserver)
            vm.error.defaultObserver(mCustomObserverProvider?.providerErrorObserver(activity)
                    ?: defaultErrorObserver)
            vm.activity.observeEvent(lifecycleOwner, defaultActivityObserver)
        }
        return vm
    }
}
package com.suifeng.kotlin.base.ui.vm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.suifeng.kotlin.base.eventbus.EventCenter
import com.suifeng.kotlin.base.ui.vm.rxlife.RxLifeViewModel
import com.suifeng.kotlin.base.ui.vm.rxlife.ViewModelEvent
import com.suifeng.kotlin.base.utils.netstatus.NetChangeObserver
import com.suifeng.kotlin.base.utils.netstatus.NetStateReceiver
import com.suifeng.kotlin.base.utils.netstatus.NetUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel, LifecycleProvider<ViewModelEvent> {

    private var isBindEventBusHere = false
    private lateinit var mNetChangeObserver: NetChangeObserver
    private val mEventBusReceiver = EventBusReceiver()
    private val lifecycleSubject: BehaviorSubject<ViewModelEvent> = BehaviorSubject.create()

    override fun onCreate() {
        lifecycleSubject.onNext(ViewModelEvent.MODEL_CREATE)
        //初始化网络监听
        initNetChangeObserver()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ViewModelEvent.MODEL_DESTROY)
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ViewModelEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifeViewModel.bindViewModel(lifecycleSubject)
    }

    override fun registerEventBus() {
        isBindEventBusHere = true
        EventBus.getDefault().register(mEventBusReceiver)
    }

    override fun unregisterEventBus() {
        isBindEventBusHere = false
        EventBus.getDefault().unregister(mEventBusReceiver)
    }

    private fun initNetChangeObserver() {
        mNetChangeObserver = object : NetChangeObserver() {
            override fun onNetConnected(type: NetUtils.NetType?) {
                onNetworkConnected(type)
            }

            override fun onNetDisConnect() {
                onNetworkDisConnected()
            }
        }
        NetStateReceiver.registerObserver(mNetChangeObserver)
    }

    open fun onNetworkConnected(type: NetUtils.NetType?) {}
    open fun onNetworkDisConnected() {}
    /**
     * EventBus发过来的事件处理
     */
    open fun onEventComing(eventCenter: EventCenter<*>) {}

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        getApplication<Application>().startActivity(Intent(getApplication(), clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(getApplication(), clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        getApplication<Application>().startActivity(intent)
    }

    private inner class EventBusReceiver {

        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEvent(eventCenter : EventCenter<*>?) {
            if(isBindEventBusHere && eventCenter != null) {
                onEventComing(eventCenter)
            }
        }
    }

}
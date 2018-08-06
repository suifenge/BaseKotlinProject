package com.suifeng.kotlin.base.ui.vm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.suifeng.kotlin.base.eventbus.EventCenter
import com.suifeng.kotlin.base.utils.netstatus.NetChangeObserver
import com.suifeng.kotlin.base.utils.netstatus.NetStateReceiver
import com.suifeng.kotlin.base.utils.netstatus.NetUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel() : IBaseViewModel {

    private lateinit var mContext: Context
    private var isBindEventBusHere = false
    private lateinit var mNetChangeObserver: NetChangeObserver
    private val mEventBusReceiver = EventBusReceiver()

    constructor(context: Context) : this() {
        this.mContext = context
    }

    override fun onCreate() {
        //初始化网络监听
        initNetChangeObserver()
    }

    override fun onDestroy() {

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
        mContext.startActivity(Intent(mContext, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(mContext, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        mContext.startActivity(intent)
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
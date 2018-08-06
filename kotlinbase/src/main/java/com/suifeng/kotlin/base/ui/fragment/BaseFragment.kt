package com.suifeng.kotlin.base.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suifeng.kotlin.base.eventbus.EventCenter
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment(
        private val layoutResId: Int,
        private vararg val clickIds: Int = intArrayOf(0)
) : RxFragment(), View.OnClickListener {

    var mRootView: View? = null
    private var isInit = false
    private val mEventBusReceiver = EventBusReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView == null) {
            mRootView = inflater.inflate(layoutResId, container, false)
        }
        if(!isInit) {
            isInit = true
            init(mRootView, savedInstanceState)
            if(isBindEventBusHere()) {
                EventBus.getDefault().register(mEventBusReceiver)
            }
            setClickViewEvent(mRootView)
        }
        return mRootView
    }

    /**
     * 初始化
     */
    abstract fun init(rootView: View?, savedInstanceState: Bundle?)
    open fun isBindEventBusHere() : Boolean { return false }
    open fun onEventComing(eventCenter: EventCenter<*>?) {}
    /**
     * 简化findViewById操作
     */
    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }

    /**
     * 简化跳转页面
     */
    fun startActivity(clazz: Class<out BaseActivity>) {
        val intent = Intent(activity, clazz)
        startActivity(intent)
    }

    /**
     * 设置
     */
    private fun setClickViewEvent(view: View?) {
        view?.let {
            Observable.create ( object : ObservableOnSubscribe<View>, View.OnClickListener {
                lateinit var emitter : ObservableEmitter<View>
                override fun subscribe(emitter: ObservableEmitter<View>) {
                    this.emitter = emitter
                    clickIds.forEach { id ->
                        if(id != 0) {
                            it.findViewById<View>(id).setOnClickListener(this)
                        }
                    }
                }

                override fun onClick(view: View) {
                    emitter.onNext(view)
                }

            }).compose(bindUntilEvent(FragmentEvent.DESTROY))
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe({onClick(it)})
        }
    }

    override fun onClick(v: View) {
    }

    override fun onDestroy() {
        if(isBindEventBusHere()) {
            EventBus.getDefault().unregister(mEventBusReceiver)
        }
        super.onDestroy()
    }

    inner class EventBusReceiver {

        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEvent(eventCenter: EventCenter<*>?) {
            if(isBindEventBusHere() && eventCenter != null) {
                onEventComing(eventCenter)
            }
        }
    }
}
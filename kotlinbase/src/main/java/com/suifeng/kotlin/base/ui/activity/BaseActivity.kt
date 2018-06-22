package com.suifeng.kotlin.base.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.suifeng.kotlin.base.R
import com.suifeng.kotlin.base.eventbus.EventCenter
import com.suifeng.kotlin.base.extension.IConfig
import com.suifeng.kotlin.base.swipback.SwipeBackActivity
import com.suifeng.kotlin.base.ui.AppManager
import com.suifeng.kotlin.base.ui.activity.BaseActivity.TransitionMode.*
import com.suifeng.kotlin.base.utils.netstatus.NetChangeObserver
import com.suifeng.kotlin.base.utils.netstatus.NetStateReceiver
import com.suifeng.kotlin.base.utils.netstatus.NetUtils
import com.suifeng.kotlin.base.utils.statusbar.StatusBarUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout
import com.zhy.autolayout.utils.AutoUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity(
    //自定义布局的id
    private val layoutResId: Int,
    //需要设置点击事件的View的Id
    private vararg val clickIds: Int = intArrayOf(0),
    //是否可以右划退出，默认是false
    private val swipeBackEnable: Boolean = false,
    //状态栏颜色
    private val statusBarColor: Int = 0,
    private val statusBarAlpha: Int = 0
) : SwipeBackActivity(), View.OnClickListener {

    private var mNetChangeObserver: NetChangeObserver? = null
    private val mEventBusReceiver = EventBusReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        if(toggleOverridePendingTransition()) {
            when(getOverridePendingTransitionMode()) {
                LEFT         -> { overridePendingTransition(R.anim.left_in, R.anim.left_out) }
                RIGHT        -> { overridePendingTransition(R.anim.right_in, R.anim.right_out) }
                TOP          -> { overridePendingTransition(R.anim.top_in, R.anim.top_out) }
                BOTTOM       -> { overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out) }
                SCALE        -> { overridePendingTransition(R.anim.scale_in, R.anim.scale_out) }
                FADE         -> { overridePendingTransition(R.anim.fade_in, R.anim.fade_out) }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        AppManager.get().addActivity(this)
        //初始化状态栏
        initStatusBar()
        //设置是否打开滑动退出
        setSwipeBackEnable(swipeBackEnable)
        //初始化EventBus
        initEventBus()
        //初始化网络监听
        initNetChangeObserver()
        //初始化Activity
        init()
        //初始化点击事件监听
        setClickViewEvent()
    }

    private fun initEventBus() {
        if(isBindEventBusHere()) {
            EventBus.getDefault().register(mEventBusReceiver)
        }
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
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver)
    }

    private fun setClickViewEvent() {
        Observable.create(object : ObservableOnSubscribe<View>, View.OnClickListener {
            lateinit var emitter: ObservableEmitter<View>
            override fun subscribe(emitter: ObservableEmitter<View>) {
                this.emitter = emitter
                clickIds.forEach { id ->
                    if(id != 0) {
                        findViewById<View>(id).setOnClickListener(this)
                    }
                }
            }

            override fun onClick(view: View) {
                emitter.onNext(view)
            }
        }).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe({onClick(it)})
    }

    open fun initStatusBar() {
        if(statusBarColor != 0) {
            StatusBarUtil.setColor(this, statusBarColor)
        } else {
            StatusBarUtil.setTranslucentForImageView(this, statusBarAlpha, findViewById(R.id.toolbar))
        }
    }

    abstract fun init()

    override fun onClick(view: View) {
        Observable.just(view)
    }

    override fun finish() {
        super.finish()
        AppManager.get().removeActivity(this)
        if(toggleOverridePendingTransition()) {
            when(getOverridePendingTransitionMode()) {
                LEFT         -> { overridePendingTransition(R.anim.left_in, R.anim.left_out) }
                RIGHT        -> { overridePendingTransition(R.anim.right_in, R.anim.right_out) }
                TOP          -> { overridePendingTransition(R.anim.top_in, R.anim.top_out) }
                BOTTOM       -> { overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out) }
                SCALE        -> { overridePendingTransition(R.anim.scale_in, R.anim.scale_out) }
                FADE         -> { overridePendingTransition(R.anim.fade_in, R.anim.fade_out) }
            }
        }
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View {
        return when(name) {
            IConfig.LAYOUT_FRAMELAYOUT      -> AutoFrameLayout(context, attrs)
            IConfig.LAYOUT_LINEARLAYOUT     -> AutoLinearLayout(context, attrs)
            IConfig.LAYOUT_RELATIVELAYOUT   -> AutoRelativeLayout(context, attrs)
            else                -> return super.onCreateView(name, context, attrs)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver)
        if(isBindEventBusHere()) {
            EventBus.getDefault().unregister(mEventBusReceiver)
        }
    }

    open fun toggleOverridePendingTransition() : Boolean { return false }
    open fun getOverridePendingTransitionMode() : TransitionMode? { return null }
    open fun isBindEventBusHere() : Boolean { return false }
    open fun onEventComing(eventCenter: EventCenter<*>?) {}
    open fun onNetworkConnected(type: NetUtils.NetType?) {}
    open fun onNetworkDisConnected() {}

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    private inner class EventBusReceiver {

        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEvent(eventCenter : EventCenter<*>?) {
            if(isBindEventBusHere() && eventCenter != null) {
                onEventComing(eventCenter)
            }
        }
    }
}
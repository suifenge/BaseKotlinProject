package com.suifeng.kotlin.base.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.suifeng.kotlin.base.BaseApplication
import com.suifeng.kotlin.base.R
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider
import com.suifeng.kotlin.base.swipback.SwipeBackActivity
import com.suifeng.kotlin.base.ui.AppManager
import com.suifeng.kotlin.base.ui.activity.BaseActivity.TransitionMode.*
import com.suifeng.kotlin.base.utils.statusbar.StatusBarUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity<V: ViewDataBinding>(
    //自定义布局的id
    private val layoutResId: Int,
    //需要设置点击事件的ViewId
    private vararg val ids: Int = intArrayOf(0),
    //是否可以右划退出，默认是true
    private val swipeBackEnable: Boolean = true,
    //状态栏颜色
    private val statusBarColor: Int = 0,
    private val statusBarAlpha: Int = 0
) : SwipeBackActivity(), View.OnClickListener {

    protected lateinit var binding: V

    protected val viewModelProvider: ViewModelProvider by lazy {
        createViewModelProvider()
    }

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
        AppManager.get().addActivity(this)
        initViewDataBinding()
        //初始化状态栏
        initStatusBar()
        //设置是否打开滑动退出
        setSwipeBackEnable(swipeBackEnable)
        //初始化Activity
        init()
        //初始化监听
        setClickViewId()
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.setLifecycleOwner(this) //绑定LiveData并对Binding设置LifecycleOwner
    }

    @SuppressLint("CheckResult")
    private fun setClickViewId() {
        Observable.create(object : ObservableOnSubscribe<View>, View.OnClickListener {
            lateinit var emitter: ObservableEmitter<View>
            override fun onClick(v: View) {
                emitter.onNext(v)
            }

            override fun subscribe(emitter: ObservableEmitter<View>) {
                this.emitter = emitter
                ids.forEach { id ->
                    if (id != 0) {
                        findViewById<View>(id).setOnClickListener(this)
                    }
                }
            }
        }).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe{ onClick(it) }
    }

    open fun initStatusBar() {
        if(statusBarColor != 0) {
            StatusBarUtil.setColor(this, statusBarColor)
        } else {
            StatusBarUtil.setTranslucentForImageView(this, statusBarAlpha, findViewById(R.id.toolbar))
        }
    }

    abstract fun init()

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

    override fun onClick(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    open fun toggleOverridePendingTransition() : Boolean { return false }
    open fun getOverridePendingTransitionMode() : TransitionMode? { return null }

    private fun createViewModelProvider(): ViewModelProvider {
        return SuperViewModelProvider(this, BaseApplication.viewModelFactory, BaseApplication.appViewModelProvider)
    }

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

}
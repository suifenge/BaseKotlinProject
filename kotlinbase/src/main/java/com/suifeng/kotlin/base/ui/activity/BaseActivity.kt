package com.suifeng.kotlin.base.ui.activity

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.View
import com.suifeng.kotlin.base.R
import com.suifeng.kotlin.base.extension.IConfig
import com.suifeng.kotlin.base.swipback.SwipeBackActivity
import com.suifeng.kotlin.base.ui.AppManager
import com.suifeng.kotlin.base.ui.activity.BaseActivity.TransitionMode.*
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider
import com.suifeng.kotlin.base.utils.statusbar.StatusBarUtil
import com.suifeng.kotlin.base.widget.auto.AutoConstraintLayout
import com.trello.rxlifecycle2.android.ActivityEvent
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
) : SwipeBackActivity(), View.OnClickListener, HasSupportFragmentInjector{

    protected lateinit var binding: V

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appViewModelProvider: ViewModelProvider

    protected val viewModelProvider: ViewModelProvider by lazy {
        createViewModelProvider()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

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
        AndroidInjection.inject(this)
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
                .subscribe({ onClick(it) })
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

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return when(name) {
            IConfig.LAYOUT_FRAMELAYOUT      -> AutoFrameLayout(context, attrs)
            IConfig.LAYOUT_LINEARLAYOUT     -> AutoLinearLayout(context, attrs)
            IConfig.LAYOUT_RELATIVELAYOUT   -> AutoRelativeLayout(context, attrs)
            IConfig.LAYOUT_CONSTRAINTLAYOUT -> AutoConstraintLayout(context, attrs)
            else                            -> super.onCreateView(name, context, attrs)
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
        return SuperViewModelProvider(this, viewModelFactory, appViewModelProvider)
    }

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

}
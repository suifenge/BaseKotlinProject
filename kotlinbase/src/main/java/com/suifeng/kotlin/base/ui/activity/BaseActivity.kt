package com.suifeng.kotlin.base.ui.activity

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.suifeng.kotlin.base.R
import com.suifeng.kotlin.base.extension.IConfig
import com.suifeng.kotlin.base.swipback.SwipeBackActivity
import com.suifeng.kotlin.base.ui.AppManager
import com.suifeng.kotlin.base.ui.activity.BaseActivity.TransitionMode.*
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.suifeng.kotlin.base.utils.statusbar.StatusBarUtil
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity<V: ViewDataBinding, out VM: BaseViewModel>(
    //自定义布局的id
    private val layoutResId: Int,
    //是否可以右划退出，默认是false
    private val swipeBackEnable: Boolean = false,
    //状态栏颜色
    private val statusBarColor: Int = 0,
    private val statusBarAlpha: Int = 0
) : SwipeBackActivity(), View.OnClickListener {

    private lateinit var binding: V
    private var viewModel: VM? = null

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
        //初始化EventBus
        initEventBus()
        //初始化Activity
        init()
        viewModel?.onCreate()
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, layoutResId)
        viewModel = initViewModel()
        binding.setVariable(initVariableId(), viewModel)
    }

    private fun initEventBus() {
        if(isBindEventBusHere()) {
            viewModel?.registerEventBus()
        }
    }

    open fun initStatusBar() {
        if(statusBarColor != 0) {
            StatusBarUtil.setColor(this, statusBarColor)
        } else {
            StatusBarUtil.setTranslucentForImageView(this, statusBarAlpha, findViewById(R.id.toolbar))
        }
    }

    abstract fun initVariableId(): Int
    abstract fun initViewModel(): VM
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
            else                            -> super.onCreateView(name, context, attrs)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isBindEventBusHere()) {
            viewModel?.unregisterEventBus()
        }
        viewModel?.onDestroy()
        viewModel = null
        binding.unbind()
    }

    open fun toggleOverridePendingTransition() : Boolean { return false }
    open fun getOverridePendingTransitionMode() : TransitionMode? { return null }
    open fun isBindEventBusHere() : Boolean { return false }
    /**
     * 刷新布局
     */
    open fun refreshLayout() {
        binding.setVariable(initVariableId(), viewModel)
    }

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

}
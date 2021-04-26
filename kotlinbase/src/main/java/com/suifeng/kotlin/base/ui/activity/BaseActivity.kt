package com.suifeng.kotlin.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.suifeng.kotlin.base.R
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider
import com.suifeng.kotlin.base.swipback.SwipeBackActivity
import com.suifeng.kotlin.base.ui.AppManager
import com.suifeng.kotlin.base.ui.activity.BaseActivity.TransitionMode.*
import com.suifeng.kotlin.base.utils.statusbar.StatusBarUtil

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity<V: ViewDataBinding, VM: BaseViewModel>(
    //自定义布局的id
    private val layoutResId: Int,
    //是否可以右划退出，默认是true
    private val swipeBackEnable: Boolean = true,
    //状态栏颜色
    private val statusBarColor: Int = 0,
    private val statusBarAlpha: Int = 0
) : SwipeBackActivity(), View.OnClickListener {

    protected lateinit var binding: V

    protected lateinit var viewModel: VM

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
        viewModel = getViewModelInstance(aspectViewModelClass())
        //初始化Activity
        init()
    }

    /**
     * 当前ViewModel的Class
     */
    abstract fun aspectViewModelClass(): Class<VM>

    /**
     * 获取ViewModel实例
     */
    private fun getViewModelInstance(action: Class<VM>): VM {
        return SuperViewModelProvider(this).get(action)
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this //绑定LiveData并对Binding设置LifecycleOwner
    }

    /**
     * 给View设置点击事件
     */
    fun setClickView(vararg views: View) {
        views.forEach {
            it.setOnClickListener(this)
        }
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

    override fun onClick(view: View) {}

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    open fun toggleOverridePendingTransition() : Boolean { return false }
    open fun getOverridePendingTransitionMode() : TransitionMode? { return null }

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

}
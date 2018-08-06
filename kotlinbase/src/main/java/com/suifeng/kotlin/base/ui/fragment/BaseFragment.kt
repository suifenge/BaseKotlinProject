package com.suifeng.kotlin.base.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment<V: ViewDataBinding, out VM: BaseViewModel>(
        private val layoutResId: Int
) : RxFragment(), View.OnClickListener {

    var mRootView: View? = null
    private var isInit = false
    private lateinit var binding: V
    private var viewModel: VM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
            viewModel = initViewModel()
            binding.setVariable(initVariableId(), viewModel)
            mRootView = binding.root
        }
        if(!isInit) {
            isInit = true
            viewModel?.onCreate()
            init(mRootView, savedInstanceState)
            if(isBindEventBusHere()) {
                viewModel?.registerEventBus()
            }
        }
        return mRootView
    }

    /**
     * 初始化ViewModel的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     */
    abstract fun initViewModel(): VM
    /**
     * 初始化
     */
    abstract fun init(rootView: View?, savedInstanceState: Bundle?)
    open fun isBindEventBusHere() : Boolean { return false }
    /**
     * 刷新布局
     */
    open fun refreshLayout() {
        binding.setVariable(initVariableId(), viewModel)
    }
    /**
     * 简化findViewById操作
     */
    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }

    override fun onDestroy() {
        if(isBindEventBusHere()) {
            viewModel?.unregisterEventBus()
        }
        super.onDestroy()
    }
}
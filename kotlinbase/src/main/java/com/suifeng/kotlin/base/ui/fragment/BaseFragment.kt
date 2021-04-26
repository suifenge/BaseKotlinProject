package com.suifeng.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment<V: ViewDataBinding, VM: BaseViewModel>(
        private val layoutResId: Int
) : Fragment(), View.OnClickListener{

    private var mRootView: View? = null
    private var isInit = false
    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
            binding.lifecycleOwner = this
            mRootView = binding.root
        }
        if(!isInit) {
            isInit = true
            viewModel = getViewModelInstance(aspectViewModelClass())
            init(mRootView, savedInstanceState)
        }
        return mRootView
    }

    /**
     * 初始化
     */
    abstract fun init(rootView: View?, savedInstanceState: Bundle?)

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
     * 给View设置点击事件
     */
    fun setClickView(vararg views: View) {
        views.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
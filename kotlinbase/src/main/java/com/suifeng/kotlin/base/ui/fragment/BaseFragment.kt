package com.suifeng.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.suifeng.kotlin.base.BaseApplication
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider
import com.trello.rxlifecycle4.android.FragmentEvent
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment<V: ViewDataBinding>(
        private val layoutResId: Int,
        private vararg val ids: Int = intArrayOf(0)
) : RxFragment(), View.OnClickListener{

    private var mRootView: View? = null
    private var isInit = false
    protected lateinit var binding: V

    protected val viewModelProvider: ViewModelProvider by lazy {
        createViewModelProvider()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
            binding.setLifecycleOwner(this)
            mRootView = binding.root
        }
        if(!isInit) {
            isInit = true
            init(mRootView, savedInstanceState)
            //初始化监听
            setClickViewId(mRootView)
        }
        return mRootView
    }

    /**
     * 初始化
     */
    abstract fun init(rootView: View?, savedInstanceState: Bundle?)

    /**
     * 简化findViewById操作
     */
    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }

    private fun setClickViewId(view: View?) {
        view?.let {
            Observable.create(object : ObservableOnSubscribe<View>, View.OnClickListener {
                lateinit var emitter: ObservableEmitter<View>
                override fun onClick(v: View) {
                    emitter.onNext(v)
                }

                override fun subscribe(emitter: ObservableEmitter<View>) {
                    this.emitter = emitter
                    ids.forEach { id ->
                        if (id != 0) {
                            it.findViewById<View>(id).setOnClickListener(this)
                        }
                    }
                }
            }).compose(bindUntilEvent(FragmentEvent.DESTROY))
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe { onClick(it) }
        }
    }

    override fun onClick(view: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    private fun createViewModelProvider(): ViewModelProvider{
        return SuperViewModelProvider(this, BaseApplication.viewModelFactory, BaseApplication.appViewModelProvider)
    }
}
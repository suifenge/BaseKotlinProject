package com.suifeng.kotlin.base.ui.fragment

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suifeng.kotlin.base.mvvm.vm.SuperViewModelProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appViewModelProvider: ViewModelProvider

    protected val viewModelProvider: ViewModelProvider by lazy {
        createViewModelProvider()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView == null) {
            AndroidSupportInjection.inject(this)
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
                    .subscribe({ onClick(it) })
        }
    }

    override fun onClick(view: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    private fun createViewModelProvider(): ViewModelProvider{
        return SuperViewModelProvider(this, viewModelFactory, appViewModelProvider)
    }
}
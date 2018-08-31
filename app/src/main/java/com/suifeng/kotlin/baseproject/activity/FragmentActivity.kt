package com.suifeng.kotlin.baseproject.activity

import android.support.v7.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityFragmentBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.fragment.DemoFragment
import com.suifeng.kotlin.baseproject.vm.FragmentViewModel
import dagger.android.support.HasSupportFragmentInjector

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_FRAGMENT)
class FragmentActivity: BaseActivity<ActivityFragmentBinding>(R.layout.activity_fragment), HasSupportFragmentInjector{


    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val viewMoel by lazy {
        viewModelProvider.get(FragmentViewModel::class.java)
    }

    override fun init() {
        initToolBar()
        val fragment = DemoFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, "demo")
                .commitAllowingStateLoss()
    }

    private fun initToolBar() {
        setToolbarTitle(mToolbar, "Fragment例子", true)
    }

}
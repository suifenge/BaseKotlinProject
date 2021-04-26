package com.suifeng.kotlin.baseproject.activity

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityFragmentBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.fragment.DemoFragment
import com.suifeng.kotlin.baseproject.vm.FragmentViewModel

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_FRAGMENT)
class FragmentActivity: BaseActivity<ActivityFragmentBinding, FragmentViewModel>(R.layout.activity_fragment){


    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
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

    override fun aspectViewModelClass(): Class<FragmentViewModel> {
        return FragmentViewModel::class.java
    }

}
package com.suifeng.kotlin.baseproject.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.suifeng.kotlin.base.BR
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.databinding.ActivityFragmentBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.fragment.DemoFragment
import com.suifeng.kotlin.baseproject.vm.FragmentViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class FragmentActivity: BaseActivity<ActivityFragmentBinding, FragmentViewModel>(R.layout.activity_fragment), HasSupportFragmentInjector{


    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun initVariableId(): Int = BR.fragmentViewModel
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun initViewModel(): FragmentViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(FragmentViewModel::class.java)
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
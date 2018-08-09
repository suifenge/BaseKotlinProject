package com.suifeng.kotlin.baseproject

import android.arch.lifecycle.ViewModelProviders
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.databinding.ActivityDemoBinding
import com.suifeng.kotlin.baseproject.vm.DemoViewModel

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */
class DemoActivity: BaseActivity<ActivityDemoBinding, DemoViewModel>(
        R.layout.activity_demo
) {
    override fun initVariableId(): Int {
        return BR.demoViewModel
    }

    override fun initViewModel(): DemoViewModel  = ViewModelProviders.of(this).get(DemoViewModel::class.java)

    override fun init() {
    }

    override fun initStatusBar() {

    }

}
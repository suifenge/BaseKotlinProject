package com.suifeng.kotlin.baseproject

import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.databinding.ActivityDemoBinding
import com.suifeng.kotlin.baseproject.vm.DemoViewModel

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */
class DemoActivity: BaseActivity<ActivityDemoBinding, DemoViewModel>(
        R.layout.activity_demo,
        R.id.btn_recycler_view, R.id.btn_net
) {
    override fun initVariableId(): Int {
        return BR.demoViewModel
    }

    override fun initViewModel(): DemoViewModel  = ViewModelProviders.of(this, viewModelFactory).get(DemoViewModel::class.java)

    override fun init() {
    }

    override fun initStatusBar() {

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_recycler_view -> viewModel?.startActivity(RecyclerActivity::class.java)
        }
    }

}
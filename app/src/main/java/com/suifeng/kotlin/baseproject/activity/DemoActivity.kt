package com.suifeng.kotlin.baseproject.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityDemoBinding
import com.suifeng.kotlin.baseproject.vm.DemoViewModel

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_DEMO)
class DemoActivity: BaseActivity<ActivityDemoBinding, DemoViewModel>(
        R.layout.activity_demo
) {

    override fun init() {
        setClickView(binding.btnRecyclerView, binding.btnNet,
            binding.btnFragment, binding.btnMultiAdapter,
        binding.btnFold)
    }

    override fun initStatusBar() {

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_recycler_view -> ARouter.getInstance().build(ARouterConfig.AR_PATH_RECYCLER).navigation(this)
            R.id.btn_net -> ARouter.getInstance().build(ARouterConfig.AR_PATH_NET).navigation(this)
            R.id.btn_fragment -> ARouter.getInstance().build(ARouterConfig.AR_PATH_FRAGMENT).navigation(this)
            R.id.btn_multi_adapter -> ARouter.getInstance().build(ARouterConfig.AR_PATH_MULTI).navigation(this)
            R.id.btn_fold -> ARouter.getInstance().build(ARouterConfig.AR_PATH_FOLD).navigation(this)
        }
    }

    override fun aspectViewModelClass(): Class<DemoViewModel> {
        return DemoViewModel::class.java
    }

}
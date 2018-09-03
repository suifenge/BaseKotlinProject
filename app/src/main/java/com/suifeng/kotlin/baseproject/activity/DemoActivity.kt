package com.suifeng.kotlin.baseproject.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityDemoBinding

/**
 * @author ljc
 * @data 2018/8/7
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_DEMO)
class DemoActivity: BaseActivity<ActivityDemoBinding>(
        R.layout.activity_demo,
        R.id.btn_recycler_view, R.id.btn_net, R.id.btn_fragment, R.id.btn_multi_adapter
) {

    override fun init() {
    }

    override fun initStatusBar() {

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_recycler_view -> ARouter.getInstance().build(ARouterConfig.AR_PATH_RECYCLER).navigation(this)
            R.id.btn_net -> ARouter.getInstance().build(ARouterConfig.AR_PATH_NET).navigation(this)
            R.id.btn_fragment -> ARouter.getInstance().build(ARouterConfig.AR_PATH_FRAGMENT).navigation(this)
            R.id.btn_multi_adapter -> ARouter.getInstance().build(ARouterConfig.AR_PATH_MULTI).navigation(this)
        }
    }

}
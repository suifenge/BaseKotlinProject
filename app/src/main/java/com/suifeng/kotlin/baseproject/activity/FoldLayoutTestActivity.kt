package com.suifeng.kotlin.baseproject.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityFoldLayoutBinding

@Route(path = ARouterConfig.AR_PATH_FOLD)
class FoldLayoutTestActivity: BaseActivity<ActivityFoldLayoutBinding, BaseViewModel>(
    R.layout.activity_fold_layout
) {
    override fun aspectViewModelClass(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun init() {

    }

    override fun initStatusBar() {

    }

}
package com.suifeng.kotlin.baseproject.activity

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityNetBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.vm.NetViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_NET)
class NetActivity: BaseActivity<ActivityNetBinding, NetViewModel>(
        R.layout.activity_net
) {
    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun init() {
        initToolBar()
        binding.netViewModel = viewModel
        setClickView(binding.btnGetWeather)
    }

    private fun initToolBar() {
        setToolbarTitle(mToolbar, "Net", true)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_get_weather -> viewModel.getWeather()
        }
    }

    override fun aspectViewModelClass(): Class<NetViewModel> {
        return NetViewModel::class.java
    }
}
package com.suifeng.kotlin.baseproject.activity

import android.support.v7.widget.Toolbar
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityNetBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.vm.NetViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
@Singleton
@Route(path = ARouterConfig.AR_PATH_NET)
class NetActivity @Inject constructor(): BaseActivity<ActivityNetBinding>(R.layout.activity_net,
    R.id.btn_get_weather
) {

    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val viewModel by lazy {
        viewModelProvider.get(NetViewModel::class.java)
    }

    override fun init() {
        initToolBar()
        binding.netViewModel = viewModel
    }

    private fun initToolBar() {
        setToolbarTitle(mToolbar, "Net", true)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_get_weather -> viewModel.getWeather()
        }
    }
}
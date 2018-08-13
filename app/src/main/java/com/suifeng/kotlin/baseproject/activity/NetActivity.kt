package com.suifeng.kotlin.baseproject.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar
import android.view.View
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.BR
import com.suifeng.kotlin.baseproject.R
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
class NetActivity @Inject constructor(): BaseActivity<ActivityNetBinding, NetViewModel>(R.layout.activity_net,
    R.id.btn_get_weather
) {

    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun initVariableId(): Int = BR.netViewModel

    override fun initViewModel(): NetViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(NetViewModel::class.java)
    }

    override fun init() {
        initToolBar()
    }

    private fun initToolBar() {
        setToolbarTitle(mToolbar, "Net", true)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_get_weather -> viewModel?.getWeather(this)
        }
    }
}
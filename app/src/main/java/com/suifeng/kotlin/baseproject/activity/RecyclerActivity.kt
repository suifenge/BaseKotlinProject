package com.suifeng.kotlin.baseproject.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.base.widget.recyclerview.HotItemDecoration
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.adapter.SimpleAdapter
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityRecyclerViewBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.vm.RecyclerViewModel

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_RECYCLER)
class RecyclerActivity: BaseActivity<ActivityRecyclerViewBinding>(
        R.layout.activity_recycler_view
) {
    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val viewModel by lazy {
        viewModelProvider.get(RecyclerViewModel::class.java)
    }

    private val mAdapter by lazy {
        SimpleAdapter(RecyclerActivity@this)
    }

    override fun init() {
        initToolBar()
        binding.recyclerViewMode = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(HotItemDecoration(this))
        binding.recyclerView.adapter = mAdapter
    }

    private fun initToolBar() {
        setToolbarTitle(mToolbar, "RecyclerView", true)
    }

    override fun toggleOverridePendingTransition(): Boolean  = true

    override fun getOverridePendingTransitionMode(): TransitionMode? = TransitionMode.FADE
}

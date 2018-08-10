package com.suifeng.kotlin.baseproject

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.base.widget.recyclerview.HotItemDecoration
import com.suifeng.kotlin.baseproject.adapter.SimpleAdapter
import com.suifeng.kotlin.baseproject.databinding.ActivityRecyclerViewBinding
import com.suifeng.kotlin.baseproject.ex.setToolbarTitle
import com.suifeng.kotlin.baseproject.vm.RecyclerViewModel

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
class RecyclerActivity: BaseActivity<ActivityRecyclerViewBinding, RecyclerViewModel>(
        R.layout.activity_recycler_view
) {
    private val mToolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }
    private val mAdapter by lazy {
        SimpleAdapter(RecyclerActivity@this, BR.dataBean, viewModel?.dataSource!!.value!!)
    }
    override fun initVariableId(): Int = BR.recyclerViewMode

    override fun initViewModel(): RecyclerViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(RecyclerViewModel::class.java)
    }

    override fun init() {
        initToolBar()
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


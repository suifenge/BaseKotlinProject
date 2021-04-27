package com.suifeng.kotlin.baseproject.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.base.widget.recyclerview.HotItemDecoration
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.adapter.NewsAdapter
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityMultiBinding
import com.suifeng.kotlin.baseproject.vm.MultiViewModel

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
@Route(path = ARouterConfig.AR_PATH_MULTI)
class MultiActivity: BaseActivity<ActivityMultiBinding, MultiViewModel>(
        R.layout.activity_multi
) {
    override fun initStatusBar() {}

    override fun init() {
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(HotItemDecoration(this))
        binding.recyclerView.adapter = NewsAdapter()
        viewModel.getNews()
    }

    override fun aspectViewModelClass(): Class<MultiViewModel> {
        return MultiViewModel::class.java
    }
}
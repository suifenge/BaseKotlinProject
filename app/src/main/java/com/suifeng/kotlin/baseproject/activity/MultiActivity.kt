package com.suifeng.kotlin.baseproject.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
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
class MultiActivity: BaseActivity<ActivityMultiBinding>(
        R.layout.activity_multi
) {

    private val viewModel by lazy {
        viewModelProvider.get(MultiViewModel::class.java)
    }
    override fun initStatusBar() {}

    override fun init() {
        binding.viewModel = viewModel
        val newsTitle = resources.getStringArray(R.array.news_title)
        newsTitle.forEach {
            val tab = binding.tabLayout.newTab()
            tab.text = it
            binding.tabLayout.addTab(tab)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(HotItemDecoration(this))
        binding.recyclerView.adapter = NewsAdapter()
        viewModel.getNews()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.changeNewsData(tab.text.toString())
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}
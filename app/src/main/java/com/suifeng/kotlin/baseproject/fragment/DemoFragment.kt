package com.suifeng.kotlin.baseproject.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.suifeng.kotlin.base.ui.fragment.BaseFragment
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.adapter.PictureAdapter
import com.suifeng.kotlin.baseproject.databinding.FragDemoBinding
import com.suifeng.kotlin.baseproject.event.RefreshLiveData
import com.suifeng.kotlin.baseproject.vm.FragDemoViewModel

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class DemoFragment: BaseFragment<FragDemoBinding, FragDemoViewModel>(R.layout.frag_demo) {

    override fun init(rootView: View?, savedInstanceState: Bundle?) {
        binding.fragDemoViewModel = viewModel
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = PictureAdapter(activity!!)
        viewModel.getPictures()
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.loadMore()
        }
        viewModel.refresh.observeEvent(this, Observer{
            it?.let {
                when(it.state) {
                    RefreshLiveData.REFRESH_SUCCESS -> binding.refreshLayout.finishRefresh(true)
                    RefreshLiveData.LOAD_MORE_SUCCESS -> binding.refreshLayout.finishLoadMore(true)
                    RefreshLiveData.REFRESH_FAILED -> binding.refreshLayout.finishRefresh(false)
                    RefreshLiveData.LOAD_MORE_FAILED -> binding.refreshLayout.finishLoadMore(false)
                    else -> {}  //do nothing
                }
            }
        })
    }

    override fun aspectViewModelClass(): Class<FragDemoViewModel> {
        return FragDemoViewModel::class.java
    }

}
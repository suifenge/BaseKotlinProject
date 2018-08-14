package com.suifeng.kotlin.baseproject.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.suifeng.kotlin.base.ui.fragment.BaseFragment
import com.suifeng.kotlin.baseproject.BR
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.adapter.PictureAdapter
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.databinding.FragDemoBinding
import com.suifeng.kotlin.baseproject.vm.FragDemoViewModel
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
class DemoFragment: BaseFragment<FragDemoBinding, FragDemoViewModel>(R.layout.frag_demo) {

    override fun initVariableId(): Int = BR.fragDemoViewModel
    @Inject
    lateinit var mAdapter: PictureAdapter

    override fun initViewModel(): FragDemoViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(FragDemoViewModel::class.java)
    }

    override fun init(rootView: View?, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = mAdapter
        viewModel?.pictureData?.observe(this, Observer<ArrayList<PictureBean.Data>> {
            mAdapter.list.clear()
            mAdapter.list.addAll(it!!)
            mAdapter.notifyDataSetChanged()
        })
        viewModel?.getPictures(this)
    }

}
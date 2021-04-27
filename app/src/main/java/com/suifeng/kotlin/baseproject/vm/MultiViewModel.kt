package com.suifeng.kotlin.baseproject.vm

import androidx.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.bean.News
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.data.RetrofitFactory
import com.suifeng.kotlin.baseproject.ex.responseError

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
class MultiViewModel: BaseViewModel() {

    val newsList = ObservableArrayList<News.Result>()

    private val mNetRepository by lazy {
        NetRepository(RetrofitFactory.newsApi)
    }

    fun getNews() {
        launchOnUI({
            val news = mNetRepository.getNews()
            newsList.addAll(news.result)
        }, {
            responseError(it)
        })
    }
}
package com.suifeng.kotlin.baseproject.vm

import android.databinding.ObservableArrayList
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.ex.convert
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.bean.NewsBean
import com.suifeng.kotlin.baseproject.data.NetRepository
import com.suifeng.kotlin.baseproject.ex.responseError
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */
class MultiViewModel @Inject constructor(application: CustomApplication, private val mNetRepository: NetRepository): BaseViewModel(application) {

    val newsList = ObservableArrayList<NewsBean.Data.DataBean>()
    private var newsBean: NewsBean? = null

    fun getNews() {
        mNetRepository.getNews()
                .convert(success = {
                    newsBean = it
                    newsList.addAll(it.data.toutiao)
                }, error = {
                    responseError(it)
                })
    }

    fun changeNewsData(text: String) {
        newsList.clear()
        when(text) {
            "头条" -> newsList.addAll(newsBean?.data?.toutiao!!)
            "科技" -> newsList.addAll(newsBean?.data?.tech!!)
            "汽车" -> newsList.addAll(newsBean?.data?.auto!!)
            "财经" -> newsList.addAll(newsBean?.data?.money!!)
            "体育" -> newsList.addAll(newsBean?.data?.sports!!)
            "要闻" -> newsList.addAll(newsBean?.data?.dy!!)
            "军事" -> newsList.addAll(newsBean?.data?.war!!)
            "娱乐" -> newsList.addAll(newsBean?.data?.ent!!)
        }
    }
}
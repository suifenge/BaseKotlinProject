package com.suifeng.kotlin.baseproject.data

import com.suifeng.kotlin.base.net.common.RetrofitClient
import com.suifeng.kotlin.baseproject.consts.Constants
import com.suifeng.kotlin.baseproject.data.api.ICommonApi

object RetrofitFactory {

    val commonApi = RetrofitClient.Builder(ICommonApi::class.java, true, Constants.BASE_URL, headers = {
            val hashMap = HashMap<String, String>()
            hashMap
        }).create()

    val picApi = RetrofitClient.Builder(ICommonApi::class.java, true, Constants.BASE_PIC_URL, headers = {
        val hashMap = HashMap<String, String>()
        hashMap
    }).create()

    val newsApi = RetrofitClient.Builder(ICommonApi::class.java, true, Constants.BASE_NEWS_URL, headers = {
        val hashMap = HashMap<String, String>()
        hashMap
    }).create()
}
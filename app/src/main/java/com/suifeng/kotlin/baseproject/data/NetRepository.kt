package com.suifeng.kotlin.baseproject.data

import com.suifeng.kotlin.baseproject.bean.News
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.bean.WeatherBean
import com.suifeng.kotlin.baseproject.data.api.ICommonApi

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class NetRepository constructor(private val commonApi: ICommonApi) {

    suspend fun getWeather(city: String): WeatherBean {
        return commonApi.getWeather(city)
    }

    suspend fun getPicture(page: Int): PictureBean {
        return commonApi.getMeitu(page)
    }

    suspend fun getNews(): News.NewsBean {
        return commonApi.getNews()
    }

}
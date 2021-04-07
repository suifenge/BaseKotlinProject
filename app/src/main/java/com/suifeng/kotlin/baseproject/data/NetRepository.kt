package com.suifeng.kotlin.baseproject.data

import com.suifeng.kotlin.baseproject.bean.NewsBean
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.bean.WeatherBean
import com.suifeng.kotlin.baseproject.data.api.ICommonApi
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class NetRepository constructor(private val commonApi: ICommonApi) {

    fun getWeather(city: String): Observable<Response<WeatherBean>> {
        return commonApi.getWeather(city)
    }

    fun getPicture(page: Int): Observable<Response<PictureBean>> {
        return commonApi.getMeitu(page)
    }

    fun getNews(): Observable<Response<NewsBean>> {
        return commonApi.getNews()
    }

}
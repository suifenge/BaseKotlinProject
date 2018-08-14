package com.suifeng.kotlin.baseproject.data

import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.bean.WeatherBean
import com.suifeng.kotlin.baseproject.data.api.ICommonApi
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class NetRepository @Inject constructor(private val commonApi: ICommonApi) {

    fun getWeather(city: String): Observable<Response<WeatherBean>> {
        return commonApi.getWeather(city)
    }

    fun getPicture(page: Int): Observable<Response<PictureBean>> {
        return commonApi.getMeitu(page)
    }

}
package com.suifeng.kotlin.baseproject.data.api

import com.suifeng.kotlin.baseproject.bean.NewsBean
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.bean.WeatherBean
import com.suifeng.kotlin.baseproject.consts.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
interface ICommonApi {

    //获取天气信息
    @GET(Constants.WEATHER_API)
    fun getWeather(@Query("city") city: String): Observable<Response<WeatherBean>>

    //获取美图
    @GET(Constants.MEI_TU_API)
    fun getMeitu(@Query("page") page: Int): Observable<Response<PictureBean>>

    @GET(Constants.NEWS_API)
    fun getNews(): Observable<Response<NewsBean>>
}
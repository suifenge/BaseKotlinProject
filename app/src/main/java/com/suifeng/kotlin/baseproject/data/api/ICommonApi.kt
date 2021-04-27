package com.suifeng.kotlin.baseproject.data.api

import com.suifeng.kotlin.baseproject.bean.News
import com.suifeng.kotlin.baseproject.bean.PictureBean
import com.suifeng.kotlin.baseproject.bean.WeatherBean
import com.suifeng.kotlin.baseproject.consts.Constants
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
    suspend fun getWeather(@Query("cityname") city: String): WeatherBean

    //获取美图
    @GET(Constants.MEI_TU_API)
    suspend fun getMeitu(@Query("page") page: Int): PictureBean

    @GET(Constants.NEWS_API)
    suspend fun getNews(): News.NewsBean
}
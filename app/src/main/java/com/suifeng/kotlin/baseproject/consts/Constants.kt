package com.suifeng.kotlin.baseproject.consts

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */
//https://www.apiopen.top/weatherApi?city=深圳
//https://www.apiopen.top/meituApi?page=1
interface Constants {

    companion object {

        const val BASE_URL = "https://www.apiopen.top/"

        const val WEATHER_API = "weatherApi"

        const val MEI_TU_API = "meituApi"

        //以上api全部来源与网络
    }
}
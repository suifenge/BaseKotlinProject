package com.suifeng.kotlin.baseproject.consts

/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

interface Constants {

    companion object {

        const val BASE_URL = "http://v.juhe.cn/"    //聚合的数据

        const val WEATHER_API = "weather/index?key=f3334acfd225eab2b1aad37baff85ba2"

        const val BASE_PIC_URL = "https://picsum.photos/"

        const val MEI_TU_API = "v2/list?limit=10"

        const val BASE_NEWS_URL = "https://api.apiopen.top/"

        const val NEWS_API = "getWangYiNews?page=1&count=20"

        //以上api全部来源与网络
    }
}
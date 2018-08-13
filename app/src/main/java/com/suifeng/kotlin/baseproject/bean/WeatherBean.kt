package com.suifeng.kotlin.baseproject.bean
import com.google.gson.annotations.SerializedName


/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

data class WeatherBean(
    @SerializedName("code") var code: Int,
    @SerializedName("msg") var msg: String,
    @SerializedName("data") var data: Data
) {

    data class Data(
        @SerializedName("yesterday") var yesterday: Yesterday,
        @SerializedName("city") var city: String,
        @SerializedName("aqi") var aqi: String,
        @SerializedName("forecast") var forecast: List<Forecast>,
        @SerializedName("ganmao") var ganmao: String,
        @SerializedName("wendu") var wendu: String
    ) {

        data class Forecast(
            @SerializedName("date") var date: String,
            @SerializedName("high") var high: String,
            @SerializedName("fengli") var fengli: String,
            @SerializedName("low") var low: String,
            @SerializedName("fengxiang") var fengxiang: String,
            @SerializedName("type") var type: String
        )


        data class Yesterday(
            @SerializedName("date") var date: String,
            @SerializedName("high") var high: String,
            @SerializedName("fx") var fx: String,
            @SerializedName("low") var low: String,
            @SerializedName("fl") var fl: String,
            @SerializedName("type") var type: String
        )
    }
}
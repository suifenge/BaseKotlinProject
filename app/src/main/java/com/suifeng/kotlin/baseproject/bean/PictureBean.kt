package com.suifeng.kotlin.baseproject.bean
import com.google.gson.annotations.SerializedName


/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

data class PictureBean(
    @SerializedName("code") var code: Int,
    @SerializedName("msg") var msg: String,
    @SerializedName("data") var data: List<Data>
) {

    data class Data(
        @SerializedName("createdAt") var createdAt: String,
        @SerializedName("publishedAt") var publishedAt: String,
        @SerializedName("type") var type: String,
        @SerializedName("url") var url: String
    )
}
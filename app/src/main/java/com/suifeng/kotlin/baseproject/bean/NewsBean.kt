package com.suifeng.kotlin.baseproject.bean
import com.google.gson.annotations.SerializedName


/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */

data class NewsBean(
    @SerializedName("code") var code: Int,
    @SerializedName("msg") var msg: String,
    @SerializedName("data") var data: Data
) {

    data class Data(
        @SerializedName("tech") var tech: List<DataBean>,
        @SerializedName("auto") var auto: List<DataBean>,
        @SerializedName("money") var money: List<DataBean>,
        @SerializedName("sports") var sports: List<DataBean>,
        @SerializedName("dy") var dy: List<DataBean>,
        @SerializedName("war") var war: List<DataBean>,
        @SerializedName("ent") var ent: List<DataBean>,
        @SerializedName("toutiao") var toutiao: List<DataBean>
    ) {

        data class DataBean(
            @SerializedName("liveInfo") var liveInfo: Any,
            @SerializedName("tcount") var tcount: Int,
            @SerializedName("picInfo") var picInfo: ArrayList<PicInfo>,
            @SerializedName("docid") var docid: String,
            @SerializedName("videoInfo") var videoInfo: Any,
            @SerializedName("channel") var channel: String,
            @SerializedName("link") var link: String,
            @SerializedName("source") var source: String,
            @SerializedName("title") var title: String,
            @SerializedName("type") var type: String,
            @SerializedName("imgsrcFrom") var imgsrcFrom: Any,
            @SerializedName("imgsrc3gtype") var imgsrc3gtype: Int,
            @SerializedName("unlikeReason") var unlikeReason: String,
            @SerializedName("digest") var digest: String,
            @SerializedName("typeid") var typeid: String,
            @SerializedName("addata") var addata: Any,
            @SerializedName("tag") var tag: String,
            @SerializedName("category") var category: String,
            @SerializedName("ptime") var ptime: String
        ) {

            data class PicInfo(
                @SerializedName("ref") var ref: Any,
                @SerializedName("width") var width: Any,
                @SerializedName("url") var url: String,
                @SerializedName("height") var height: Any
            )
        }
    }
}
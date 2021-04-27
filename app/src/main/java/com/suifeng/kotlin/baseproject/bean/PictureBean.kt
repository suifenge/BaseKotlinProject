package com.suifeng.kotlin.baseproject.bean


/**
 * @author ljc
 * @data 2018/8/13
 * @describe
 */

class PictureBean : ArrayList<PictureBeanItem>()

data class PictureBeanItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
package com.suifeng.kotlin.baseproject.bean


/**
 * @author ljc
 * @data 2018/9/3
 * @describe
 */

class News {
    data class NewsBean(
            val code: Int,
            val message: String,
            val result: List<Result>
    )

    data class Result(
            val image: String,
            val passtime: String,
            val path: String,
            val title: String
    )
}

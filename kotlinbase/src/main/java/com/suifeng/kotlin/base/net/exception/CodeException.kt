package com.suifeng.kotlin.base.net.exception

enum class CodeException{
    /** 网络异常  */
    InternetException,
    /** Gson解析错误  */
    JsonException,
    /** 无网络但是有缓存  */
    InternetCacheException,
    /** 未知异常  */
    OtherException,
    /** 响应吗异常,code < 200 || code > 300  */
    NotSuccessfulException
}
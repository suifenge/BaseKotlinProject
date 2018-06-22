package com.suifeng.kotlin.base.net.exception

class ApiException(
        val code: CodeException,
        // 状态吗
        val statusCode:Int,
        // 数据
        val displayMessage:String
):Throwable()
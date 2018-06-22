package com.suifeng.kotlin.base.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.HashMap


/**
 * @author admin
 * @data 2018/4/25
 * @describe
 */
class HeaderInterceptor(private val headers: () -> HashMap<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        headers().entries.forEach {
            if (it.value.isNotEmpty()) {
                builder.addHeader(it.key, it.value)
            }
        }
        return chain.proceed(builder.url(request.url()).build())
    }

}
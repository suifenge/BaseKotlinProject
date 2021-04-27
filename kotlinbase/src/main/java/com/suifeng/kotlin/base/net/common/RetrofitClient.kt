package com.suifeng.kotlin.base.net.common

import com.suifeng.kotlin.base.net.interceptor.HeaderInterceptor
import com.suifeng.kotlin.base.utils.log.KLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/22
 * @describe
 */
class RetrofitClient {
    companion object {
        /**
         * 初始化创建Retrofit 对象
         */
        fun <T> createRetrofit(builder: Builder<T>, cls: Class<T>): T {
            KLog.i("创建 createRetrofit")
            // 构建 log
            val loggingInterceptor = if (builder.debug) {
                HttpLoggingInterceptor {
                    KLog.i(it)
                }
            } else {
                null
            }

            //初始化OkHttp
            val okHttpBuilder = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .readTimeout(builder.readTimeout, TimeUnit.SECONDS)
                    .connectTimeout(builder.connectionTimeout, TimeUnit.SECONDS)
            // header
            okHttpBuilder.addInterceptor(HeaderInterceptor(builder.headers))
            // log
            loggingInterceptor?.apply {
                level = HttpLoggingInterceptor.Level.BODY
                okHttpBuilder.addInterceptor(this)
            }
            // 构建
            return Retrofit.Builder()
                    //增加返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpBuilder.build())
                    // 多域名
                    .baseUrl(builder.baseUrl)
                    .build()
                    .create(cls)
        }
    }


    /**
     * Builder 模式构建配置
     */
    class Builder<out T>(
            private val cls: Class<T>,
            // 是否开启调试输出
            val debug: Boolean = false,
            // 当前http网络请求
            val baseUrl: String,
            // 读取超时 - 默认6秒
            val readTimeout: Long = 6,
            // 超时链接 - 默认6秒
            val connectionTimeout: Long = 6,
            // 头部
            val headers: () -> HashMap<String, String>
    ) {

        fun create() = createRetrofit(this, cls)
    }
}
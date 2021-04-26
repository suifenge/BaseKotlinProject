package com.suifeng.kotlin.base.net.ex

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.common.ResponseListener
import com.suifeng.kotlin.base.net.common.ResponseSubscriber
import com.suifeng.kotlin.base.net.transformer.ApplySchedulers
import com.suifeng.kotlin.base.net.transformer.ConvertSchedulers
import com.suifeng.kotlin.base.utils.log.KLog
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

public inline fun <T> Observable<T>.apply(
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ApplySchedulers())
            .subscribe(ResponseSubscriber(responseListener = object : ResponseListener<T> {

                override fun onSucceed(data: T) {
                    try {
                        success(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Throwable) {
                    try {
                        error(exception)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        KLog.e("onError函数调用奔溃")
                    }
                }

                override fun onComplete() {
                    complete()
                }
            }))
}

public inline fun <T> Observable<Response<T>>.convert(
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ConvertSchedulers())
            .subscribe(ResponseSubscriber(responseListener = object :
                    ResponseListener<T> {

                override fun onSucceed(data: T) {
                    try {
                        success(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onError(e)
                    }
                }

                override fun onError(exception: Throwable) {
                    try {
                        error(exception)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        KLog.e("onError函数调用奔溃")
                    }
                }

                override fun onComplete() {
                    complete()
                }
            }))
}

/**
 * ViewModel网络请求拓展
 */
public inline fun <T> Observable<Response<T>>.convert(
        rx: BaseViewModel,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ConvertSchedulers())
            .subscribe(ResponseSubscriber(responseListener = object :
                    ResponseListener<T> {

                override fun onSucceed(data: T) {
                    try {
                        success(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onError(e)
                    }
                }

                override fun onError(exception: Throwable) {
                    try {
                        error(exception)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        KLog.e("onError函数调用奔溃")
                    }
                }

                override fun onComplete() {
                    complete()
                }
            }))
}

/**
 * 当前网络状态是否在线
 */
@SuppressLint("MissingPermission")
fun Context.isNetworkAvailable(): Boolean {
    try {
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE)
        if (connectivity != null) {
            connectivity as ConnectivityManager
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return false
}
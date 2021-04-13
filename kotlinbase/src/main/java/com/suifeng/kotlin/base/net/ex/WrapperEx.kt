package com.suifeng.kotlin.base.net.ex

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.net.common.ProgressSubscriber
import com.suifeng.kotlin.base.net.common.ResponseListener
import com.suifeng.kotlin.base.net.common.ResponseSubscriber
import com.suifeng.kotlin.base.net.interfac.IDialog
import com.suifeng.kotlin.base.net.transformer.ApplySchedulers
import com.suifeng.kotlin.base.net.transformer.ConvertSchedulers
import com.suifeng.kotlin.base.net.transformer.WrapperSchedulers
import com.suifeng.kotlin.base.utils.log.KLog
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.android.FragmentEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxDialogFragment
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import retrofit2.Response

public inline fun <T> Observable<T>.apply(
        // 必传对象，用于控制声明周期
        rx: RxAppCompatActivity,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ApplySchedulers())
            .compose(rx.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(ProgressSubscriber(rx.supportFragmentManager, iDialog = iDialog, responseListener = object : ResponseListener<T> {

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

/**
 * activity网络请求扩展
 */
public inline fun <T> Observable<Response<T>>.convert(
        // 必传对象，用于控制声明周期
        rx: RxAppCompatActivity,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(ProgressSubscriber(rx.supportFragmentManager, iDialog = iDialog, responseListener = object :
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
            .compose(rx.bindToLifecycle())
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
 * fragment网络请求扩展
 */
public inline fun <T> Observable<T>.apply(
        // 必传对象，用于控制声明周期
        rx: RxFragment,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ApplySchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY))
            .subscribe(ProgressSubscriber(rx.fragmentManager!!, iDialog = iDialog, responseListener = object : ResponseListener<T> {

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

public inline fun <T> Observable<Response<T>>.convert(
        // 必传对象，用于控制声明周期
        rx: RxFragment,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY))
            .subscribe(ProgressSubscriber(rx.fragmentManager!!, iDialog = iDialog, responseListener = object : ResponseListener<T> {

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
 * DialogFragment网络请求扩展
 */
public inline fun <T> Observable<T>.apply(
        // 必传对象，用于控制声明周期
        rx: RxDialogFragment,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ApplySchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            .subscribe(ProgressSubscriber(rx.fragmentManager!!, iDialog = iDialog, responseListener = object : ResponseListener<T> {

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
 * DialogFragment网络请求扩展
 */
public inline fun <T> Observable<Response<T>>.convert(
        // 必传对象，用于控制声明周期
        rx: RxDialogFragment,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        noinline success: (T) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {
    this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            .subscribe(ProgressSubscriber(rx.fragmentManager!!, iDialog = iDialog, responseListener = object : ResponseListener<T> {

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
 * Activity扩展，加入生命周期控制，并没有重试操作
 * @param rx life
 */
public inline fun <T> Observable<T>.wrapper(rx: RxAppCompatActivity): Observable<T> {
    return this.compose(WrapperSchedulers())
            .compose(rx.bindUntilEvent(ActivityEvent.DESTROY))
}

/**
 * Activity扩展，加入生命周期控制，有重试操作
 * @param rx life
 */
public inline fun <T> Observable<Response<T>>.convertRequest(rx: RxAppCompatActivity): Observable<T> {
    return this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(ActivityEvent.DESTROY))
}

/**
 * Fragment扩展，加入生命周期控制，并没有重试操作
 * @param rx life
 */
public inline fun <T> Observable<T>.wrapper(rx: RxFragment): Observable<T> {
    return this.compose(WrapperSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY))
}

/**
 * Fragment扩展，加入生命周期控制，有重试操作
 * @param rx life
 */
public inline fun <T> Observable<Response<T>>.convertRequest(rx: RxFragment): Observable<T> {
    return this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY))
}


/**
 * DialogFragment扩展，加入生命周期控制，并没有重试操作
 * @param rx life
 */
public inline fun <T> Observable<T>.wrapper(rx: RxDialogFragment): Observable<T> {
    return this.compose(WrapperSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
}


/**
 * DialogFragment扩展，加入生命周期控制，并没有重试操作
 * @param rx life
 */
public inline fun <T> Observable<Response<T>>.convertRequest(rx: RxDialogFragment): Observable<T> {
    return this.compose(ConvertSchedulers())
            .compose(rx.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
}


/**
 * 组合 zip操作符号
 */
public inline fun <T1, T2> RxFragment.convertZip(
        t1: Observable<Response<T1>>,
        t2: Observable<Response<T2>>,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        crossinline success: (T1, T2) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {

    Observable.zip(t1.convertRequest(this), t2.convertRequest(this), BiFunction<T1, T2, ArrayList<Any>> { t1, t2 ->
        val arrayList = ArrayList<Any>()
        arrayList.add(t1 as Any)
        arrayList.add(t2 as Any)
        arrayList
    }).subscribe(ProgressSubscriber(this.fragmentManager!!, iDialog, responseListener = object : ResponseListener<ArrayList<Any>> {
        override fun onSucceed(data: ArrayList<Any>) {
            try {
                success(data[0] as T1, data[1] as T2)
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
 * 组合 zip操作符号
 */
public inline fun <T1, T2> RxAppCompatActivity.convertZip(
        t1: Observable<Response<T1>>,
        t2: Observable<Response<T2>>,
        // dialog呈现方式，三种：UN_LOADING(不显示),NORMAL_LOADING(显示可关闭),NORMAL_LOADING(显示可关闭),FORBID_LOADING(显示不关闭)
        iDialog: IDialog = IDialog.FORBID_LOADING,
        // 成功回调
        crossinline success: (T1, T2) -> Unit,
        // 失败回调
        noinline error: (Throwable) -> Unit = {},
        // 成功后，并执行完 success 方法后回调
        noinline complete: () -> Unit = {}
) {

    Observable.zip(t1.convertRequest(this), t2.convertRequest(this), BiFunction<T1, T2, ArrayList<Any>> { t1, t2 ->
        val arrayList = ArrayList<Any>()
        arrayList.add(t1 as Any)
        arrayList.add(t2 as Any)
        arrayList
    }).subscribe(ProgressSubscriber(this.supportFragmentManager!!, iDialog, responseListener = object : ResponseListener<ArrayList<Any>> {
        override fun onSucceed(data: ArrayList<Any>) {
            try {
                success(data[0] as T1, data[1] as T2)
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }

        override fun onError(exception: Throwable) {
            error(exception)
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
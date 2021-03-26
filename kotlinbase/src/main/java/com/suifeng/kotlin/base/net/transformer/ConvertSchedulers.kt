package com.suifeng.kotlin.base.net.transformer

import com.suifeng.kotlin.base.net.common.RetryWhenNetwork
import com.suifeng.kotlin.base.net.exception.ApiException
import com.suifeng.kotlin.base.net.exception.CodeException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class ConvertSchedulers<T> : ObservableTransformer<Response<T>, T> {
    override fun apply(upstream: Observable<Response<T>>): ObservableSource<T> =
            upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .flatMap { response ->
                        if (response.isSuccessful) {
                            Observable.just(response.body()!!)
                        } else {
                            val code = response.code()
                            val string = response.errorBody()!!.string()
                            Observable.error(ApiException(CodeException.NotSuccessfulException, code, string))
                        }
                    }.observeOn(AndroidSchedulers.mainThread(),true)
                    .retryWhen(RetryWhenNetwork())
}
package com.suifeng.kotlin.base.net.transformer

import com.suifeng.kotlin.base.net.common.RetryWhenNetwork
import com.suifeng.kotlin.base.net.exception.ApiException
import com.suifeng.kotlin.base.net.exception.CodeException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ConvertSchedulers<T> : ObservableTransformer<Response<T>,T>{
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
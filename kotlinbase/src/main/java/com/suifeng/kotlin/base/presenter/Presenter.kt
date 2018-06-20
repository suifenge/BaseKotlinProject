package com.suifeng.kotlin.base.presenter

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
interface Presenter<V : MvpView> {
    fun attachView(V: MvpView)
    fun detachView()
}
package com.suifeng.kotlin.base.presenter

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
interface Presenter<in V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}
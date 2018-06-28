package com.suifeng.kotlin.base.presenter

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
interface IPresenter<in V : IBaseView> {
    fun attachView(mRootView: V)
    fun detachView()
}
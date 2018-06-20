package com.suifeng.kotlin.base.presenter

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class BasePresenter<T : MvpView> : Presenter<T> {
    
    override fun attachView(V: MvpView) {

    }

    override fun detachView() {

    }

}
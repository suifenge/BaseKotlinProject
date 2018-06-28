package com.suifeng.kotlin.base.presenter

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class BasePresenter<T : IBaseView>(val provider: LifecycleProvider<ActivityEvent>) : IPresenter<T> {

    var mMvpView: T? = null
        private set

    val isViewAttached: Boolean
        get() = mMvpView != null

    override fun attachView(mvpView: T) {
        this.mMvpView = mvpView
    }

    override fun detachView() {
        mMvpView = null
    }

    fun checkViewAttached() {
        if(!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Please call IPresenter.attachView(IBaseView) before requesting data to the IPresenter")
}
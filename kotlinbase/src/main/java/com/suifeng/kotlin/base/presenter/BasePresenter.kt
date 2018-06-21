package com.suifeng.kotlin.base.presenter

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class BasePresenter<T : MvpView>(val provider: LifecycleProvider<ActivityEvent>) : Presenter<T> {

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

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
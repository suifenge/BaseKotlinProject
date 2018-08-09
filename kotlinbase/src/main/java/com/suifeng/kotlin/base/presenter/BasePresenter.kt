package com.suifeng.kotlin.base.presenter

import com.suifeng.kotlin.base.presenter.rxlife.PresenterEvent
import com.suifeng.kotlin.base.presenter.rxlife.RxLifePresenter
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class BasePresenter<T : IBaseView> : IPresenter<T>, LifecycleProvider<PresenterEvent>{

    var mRootView: T? = null
        private set
    private val lifecycleSubject: BehaviorSubject<PresenterEvent> = BehaviorSubject.create()

    private val isViewAttached: Boolean
        get() = mRootView != null

    override fun attachView(mRootView: T) {
        lifecycleSubject.onNext(PresenterEvent.VIEW_ATTACH)
        this.mRootView = mRootView
    }

    override fun detachView() {
        lifecycleSubject.onNext(PresenterEvent.VIEW_DETACH)
        mRootView = null
    }

    fun checkViewAttached() {
        if(!isViewAttached) throw MvpViewNotAttachedException()
    }

    override fun lifecycle(): Observable<PresenterEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: PresenterEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifePresenter.bindViewModel(lifecycleSubject)
    }

    class MvpViewNotAttachedException : RuntimeException("Please call IPresenter.attachView(IBaseView) before requesting data to the IPresenter")
}
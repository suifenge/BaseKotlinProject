package com.suifeng.kotlin.base.presenter.rxlife

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author ljc
 * @data 2018/8/9
 * @describe
 */
object RxLifePresenter {
    fun <T> bindViewModel(lifecycle: Observable<PresenterEvent>): LifecycleTransformer<T> {
        return RxLifecycle.bind(lifecycle, VIEW_LIFECYCLE)
    }

    private val VIEW_LIFECYCLE: Function<PresenterEvent, PresenterEvent> = Function { lastEvent ->
        when(lastEvent) {
            PresenterEvent.VIEW_ATTACH     -> PresenterEvent.VIEW_DETACH
            PresenterEvent.VIEW_DETACH    -> throw UnsupportedOperationException("Cannot bind to Fragment lifecycle when outside of it.")
        }
    }
}
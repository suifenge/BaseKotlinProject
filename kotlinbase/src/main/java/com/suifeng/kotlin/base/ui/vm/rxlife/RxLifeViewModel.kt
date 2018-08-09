package com.suifeng.kotlin.base.ui.vm.rxlife

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author ljc
 * @data 2018/8/8
 * @describe
 */

object RxLifeViewModel {

    fun <T> bindViewModel(lifecycle: Observable<ViewModelEvent>): LifecycleTransformer<T> {
        return RxLifecycle.bind(lifecycle, VIEW_LIFECYCLE)
    }

    private val VIEW_LIFECYCLE: Function<ViewModelEvent, ViewModelEvent> = Function { lastEvent ->
        when(lastEvent) {
            ViewModelEvent.MODEL_CREATE     -> ViewModelEvent.MODEL_DESTROY
            ViewModelEvent.MODEL_DESTROY    -> throw UnsupportedOperationException("Cannot bind to Fragment lifecycle when outside of it.")
        }
    }

}
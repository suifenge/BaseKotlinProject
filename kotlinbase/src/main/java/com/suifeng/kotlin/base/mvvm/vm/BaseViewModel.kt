package com.suifeng.kotlin.base.mvvm.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.suifeng.kotlin.base.lifecycle.RxLifecycleViewModel
import com.suifeng.kotlin.base.lifecycle.ViewModelEvent
import com.suifeng.kotlin.base.mvvm.livedata.ActivityLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ProgressLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleProvider<ViewModelEvent>{

    private val lifecycleSubject = BehaviorSubject.create<ViewModelEvent>()

    val progress = ProgressLiveData()
    val error = ErrorLiveData()
    val toast = ToastLiveData()
    val activity = ActivityLiveData()

    override fun onCleared() {
        super.onCleared()
        lifecycleSubject.onNext(ViewModelEvent.CLEAR)
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ViewModelEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleViewModel.bindViewModel(lifecycleSubject)
    }
}
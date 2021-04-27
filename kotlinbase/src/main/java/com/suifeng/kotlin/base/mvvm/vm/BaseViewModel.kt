package com.suifeng.kotlin.base.mvvm.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suifeng.kotlin.base.mvvm.livedata.ActivityLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ErrorLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ProgressLiveData
import com.suifeng.kotlin.base.mvvm.livedata.ToastLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel: ViewModel(){

    val progress = ProgressLiveData()
    val error = ErrorLiveData()
    val toast = ToastLiveData()
    val activity = ActivityLiveData()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit,
                   catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {},
                   finallyBlock: suspend CoroutineScope.() -> Unit = {}): Job {
        return tryCatch(Dispatchers.Main, block, catchBlock, finallyBlock)
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit,
                   catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {},
                   finallyBlock: suspend CoroutineScope.() -> Unit = {}): Job {
        return tryCatch(Dispatchers.IO, block, catchBlock, finallyBlock)
    }

    private fun tryCatch(context: CoroutineContext,
                                 tryBlock: suspend CoroutineScope.() -> Unit,
                                 catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit,
                                 finallyBlock: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(context) {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }
}
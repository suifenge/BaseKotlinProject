package com.suifeng.kotlin.base.net.common

import androidx.fragment.app.FragmentManager
import com.suifeng.kotlin.base.net.interfac.IDialog
import com.suifeng.kotlin.base.widget.dialog.ProgressDialogFragment
import com.suifeng.kotlin.base.extension.IConfig.Companion.NET_PROGRESS_TAG
import com.suifeng.kotlin.base.utils.log.KLog
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class ProgressSubscriber<T>(
        //上下文对象
        private val fragment: FragmentManager,
        /* 显示dialog的方式 */
        private val iDialog: IDialog = IDialog.UN_LOADING,
        /* 回调 */
        private val responseListener: ResponseListener<T>
) : Observer<T> {
    /* Disposable */
    private var disposable: Disposable? = null
    /* 加载进度条 */
    private val progressDialog by lazy { ProgressDialogFragment() }

    override fun onSubscribe(d: Disposable) {
        disposable = d
        //显示进度条，如果有的话
        showProgressDialog()
    }

    override fun onNext(t: T) {
        responseListener.onSucceed(t)
    }

    override fun onComplete() {
        //销毁进度条，如果有的话
        dismissDialog()
        //回调
        responseListener.onComplete()
    }

    override fun onError(e: Throwable) {
        //销毁进度条，如果有的话
        dismissDialog()
        // 回调失败
        responseListener.onError(e)
    }


    /** 显示ProgressDialog */
    private fun showProgressDialog() {
        if (iDialog != IDialog.UN_LOADING) {
            when (iDialog) {
                IDialog.NORMAL_LOADING -> progressDialog.isCancelable = true
                IDialog.FORBID_LOADING -> progressDialog.isCancelable = false
                else -> {
                    //什么都不干，暂时
                }
            }
            progressDialog.show(fragment, NET_PROGRESS_TAG)
            progressDialog.setDismissCallback {
                if (disposable != null) {
                    KLog.i("销毁进度条，并关闭异步!!!")
                    disposable?.dispose()
                }
            }
        }
    }

    /** 销毁dialog */
    private fun dismissDialog() {
        disposable = null
        if (iDialog != IDialog.UN_LOADING && progressDialog.isVisible) {
            progressDialog.dismiss()
        }
    }
}
package com.suifeng.kotlin.baseproject.vm

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.base.extension.IToast
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.baseproject.CustomApplication
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
class LoginViewModel: BaseViewModel() {

    val userName = ObservableField<String>()
    val password = ObservableField<String>()

    //清除按钮visibility状态
    val clearBtnVisibility = ObservableField<Int>(View.VISIBLE)

    val passwordVisibility = ObservableBoolean(false)

    //模拟登录
    @SuppressLint("CheckResult")
    fun login() {
        if(TextUtils.isEmpty(userName.get())) {
            toast.show("请输入账号!", IToast.ERROR)
            return
        }
        if(TextUtils.isEmpty(password.get())) {
            toast.show("请输入密码!", IToast.ERROR)
            return
        }
        //登录跳转
        progress.show("登录中...")
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe ({
                    progress.hide()
                    toast.show("登录成功")
                    next()
                    activity.finish()
                }, {
                    error.call(0, it.localizedMessage)
                })
    }

    private fun next() {
        ARouter.getInstance().build(ARouterConfig.AR_PATH_DEMO)
                .navigation()
    }

    fun switchPasswordVisibility() {
        passwordVisibility.set(!passwordVisibility.get())
    }

    fun clearUserName() {
        userName.set("")
    }

    fun clearUserNameBtnVisibility(visibility: Int) {
        clearBtnVisibility.set(visibility)
    }

    fun isPasswordVisibility(): Boolean {
        return passwordVisibility.get()
    }
}
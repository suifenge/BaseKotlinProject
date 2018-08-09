package com.suifeng.kotlin.baseproject.vm

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.text.TextUtils
import com.suifeng.kotlin.base.ui.vm.BaseViewModel
import com.suifeng.kotlin.base.ui.vm.rxlife.ViewModelEvent
import com.suifeng.kotlin.baseproject.DemoActivity
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */

class LoginViewModel(application: Application): BaseViewModel(application) {
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    //清除按钮visibility状态
    val clearBtnVisibility = MutableLiveData<Int>()

    val passwordVisibility = ObservableBoolean(false)

    val progressDialogShow = ObservableBoolean(false)
    //模拟登录
    fun login() {
        if(TextUtils.isEmpty(userName.value)) {
            Toasty.error(getApplication(), "请输入账号!").show()
            return
        }
        if(TextUtils.isEmpty(password.value)) {
            Toasty.error(getApplication(), "请输入密码!").show()
            return
        }
        //登录跳转
        progressDialogShow.set(true)
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(bindUntilEvent(ViewModelEvent.MODEL_DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    progressDialogShow.set(false)
                    startActivity(DemoActivity::class.java)
                }
    }

    fun switchPasswordVisibility() {
        passwordVisibility.set(!passwordVisibility.get())
    }

    fun clearUserName() {
        userName.value = ""
    }

    fun clearUserNameBtnVisibility(visibility: Int) {
        clearBtnVisibility.value = visibility
    }

    fun isPasswordVisibility(): Boolean {
        return passwordVisibility.get()
    }
}
package com.suifeng.kotlin.baseproject.vm

import android.Manifest
import android.text.TextUtils
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.suifeng.kotlin.base.extension.IToast
import com.suifeng.kotlin.base.mvvm.vm.BaseViewModel
import com.suifeng.kotlin.base.permissions.PermissionManager
import com.suifeng.kotlin.base.utils.log.KLog
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import kotlinx.coroutines.delay

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
        launchOnUI(
                {
                    delay(1500)
                    toast.show("登录成功")
                    next()
                    activity.finish()
                }, {error.call(0, it.localizedMessage)}, {progress.hide()}
        )
    }

    private fun next() {
        ARouter.getInstance().build(ARouterConfig.AR_PATH_DEMO)
                .navigation()
    }

    private val permsSd = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun requestPermissions(parent: FragmentActivity) {
        launchOnUI({
            val permissions = PermissionManager.getPermissionManager().requestPermissions(parent, permsSd)
            permissions.forEach {
                KLog.i("wtf","---->$it\n")
            }
        })
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
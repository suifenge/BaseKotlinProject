package com.suifeng.kotlin.baseproject.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.databinding.Observable
import com.alibaba.android.arouter.facade.annotation.Route
import com.suifeng.kotlin.base.extension.bindKeyboardLayout
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.baseproject.R
import com.suifeng.kotlin.baseproject.consts.ARouterConfig
import com.suifeng.kotlin.baseproject.databinding.ActivityMainBinding
import com.suifeng.kotlin.baseproject.vm.LoginViewModel

@Route(path = ARouterConfig.AR_PATH_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding>(
        R.layout.activity_main,
        R.id.iv_clear,
        R.id.iv_switch_password,
        R.id.btn_login,
        swipeBackEnable = false
) {

    private val viewModel by lazy {
        viewModelProvider.get(LoginViewModel::class.java)
    }

    override fun init() {
        binding.viewModel = viewModel
        //内容布局、按钮和键盘进行绑定，不遮盖按钮
        binding.llContent.bindKeyboardLayout(binding.btnLogin)
        binding.edtUsername.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            val visibilityValue = if(hasFocus) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            viewModel.clearUserNameBtnVisibility(visibilityValue)
        }
        viewModel.passwordVisibility.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel.isPasswordVisibility()) {
                    //密码可见
                    binding.ivSwitchPassword.setImageResource(R.mipmap.show_psw_press)
                    binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    binding.ivSwitchPassword.setImageResource(R.mipmap.show_psw)
                    binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        })
    }

    override fun initStatusBar() {
        //如果界面没有自定义的ToolBar 必须重写这个方法
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_clear -> {
                viewModel.clearUserName()
            }
            R.id.iv_switch_password -> {
                viewModel.switchPasswordVisibility()
            }
            R.id.btn_login -> {
                viewModel.login()
            }
        }
    }
}


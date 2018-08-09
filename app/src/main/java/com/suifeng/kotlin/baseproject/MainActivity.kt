package com.suifeng.kotlin.baseproject

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.Observable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.suifeng.kotlin.base.extension.bindKeyboardLayout
import com.suifeng.kotlin.base.ui.activity.BaseActivity
import com.suifeng.kotlin.base.utils.log.KLog
import com.suifeng.kotlin.baseproject.databinding.ActivityMainBinding
import com.suifeng.kotlin.baseproject.vm.LoginViewModel
import com.suifeng.kotlin.baseproject.vm.ViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding, LoginViewModel>(
        R.layout.activity_main,
        R.id.iv_clear,
        R.id.iv_switch_password,
        R.id.btn_login,
        swipeBackEnable = false
) {
    override fun initVariableId(): Int = BR.viewModel

    override fun initViewModel(): LoginViewModel {
        return ViewModelProviders.of(this, ViewModelFactory(this.application)).get(LoginViewModel::class.java)
    }

    override fun init() {
        //内容布局、按钮和键盘进行绑定，不遮盖按钮
        binding.llContent.bindKeyboardLayout(binding.btnLogin)
        binding.edtUsername.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            val visibilityValue = if(hasFocus) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            viewModel?.clearUserNameBtnVisibility(visibilityValue)
        }
        viewModel?.passwordVisibility?.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel != null && viewModel!!.isPasswordVisibility()) {
                    //密码可见
                    binding.ivSwitchPassword.setImageResource(R.mipmap.show_psw_press)
                    binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    binding.ivSwitchPassword.setImageResource(R.mipmap.show_psw)
                    binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        })
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("登录中...")
        progressDialog.setCancelable(false)
        viewModel?.progressDialogShow!!.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel?.progressDialogShow!!.get()) {
                    progressDialog.show()
                } else {
                    progressDialog.dismiss()
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
                KLog.i("wtf", "清除按钮点击")
                viewModel?.clearUserName()
            }
            R.id.iv_switch_password -> {
                viewModel?.switchPasswordVisibility()
            }
            R.id.btn_login -> {
                viewModel?.login()
            }
        }
    }
}


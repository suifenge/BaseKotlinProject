package com.suifeng.kotlin.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

abstract class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApp()
        val viewModelStore = ViewModelStore()  //提供全局的ViewModelStore
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(instance)
        appViewModelProvider = ViewModelProvider(viewModelStore, viewModelFactory)
    }

    abstract fun initApp()

    companion object {
        @JvmStatic lateinit var instance: Application

        @JvmStatic lateinit var appViewModelProvider: ViewModelProvider

        @JvmStatic lateinit var viewModelFactory: ViewModelProvider.Factory

    }

}
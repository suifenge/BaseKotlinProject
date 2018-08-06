package com.suifeng.kotlin.base.ui.vm

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
interface IBaseViewModel {

    /**
     * View界面创建
     */
    fun onCreate()

    /**
     * View界面销毁
     */
    fun onDestroy()

    /**
     * 注册EventBus
     */
    fun registerEventBus()

    /**
     * 注销EventBus
     */
    fun unregisterEventBus()
}
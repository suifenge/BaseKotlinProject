package com.suifeng.kotlin.base.eventbus

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
class EventCenter<T>(val eventCode: Int = -1, val data: T? = null)
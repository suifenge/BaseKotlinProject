package com.suifeng.kotlin.base.binding

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
interface BindingFunction<out T> {
    fun call(): T
}
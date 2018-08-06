package com.suifeng.kotlin.base.binding

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
class BindingCommand<T> {
    private var mExecute: BindingAction? = null
    private var mConsumer: BindingConsumer<T>? = null
    private var mCanExecute: BindingFunction<Boolean>? = null

    constructor(execute: BindingAction) {
        this.mExecute = execute
    }

    constructor(execute: BindingConsumer<T>) {
        this.mConsumer = execute
    }

    constructor(execute: BindingAction, canExecute: BindingFunction<Boolean>) {
        this.mExecute = execute
        this.mCanExecute = canExecute
    }

    constructor(execute: BindingConsumer<T>, canExecute: BindingFunction<Boolean>) {
        this.mConsumer = execute
        this.mCanExecute = canExecute
    }

    /**
     * 执行BindingAction命令
     */
    public fun execute() {
        if(canExecute()) {
            mExecute?.call()
        }
    }

    /**
     * 执行带泛型参数的命令
     * @param parameter 泛型参数
     */
    public fun execute(parameter: T) {
        if(canExecute()) {
            mConsumer?.call(parameter)
        }
    }

    /**
     * 是否需要执行
     * @return true则执行, 反之不执行
     */
    private fun canExecute(): Boolean {
        if(mCanExecute == null) {
            return true
        }
        return mCanExecute!!.call()
    }
}
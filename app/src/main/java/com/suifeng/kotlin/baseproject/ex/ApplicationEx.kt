package com.suifeng.kotlin.baseproject.ex

import android.app.Application
import android.content.Context
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator

/**
 * @author ljc
 * @data 2018/8/31
 * @describe
 */
public inline fun Application.initThird() {
    //SmartRefreshLayout 配置全局
    //配置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreator(object: DefaultRefreshFooterCreator {
        override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
            return ClassicsFooter(context)
        }
    })
    //配置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(object: DefaultRefreshHeaderCreator {
        override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
            return ClassicsHeader(context)
        }

    })
}
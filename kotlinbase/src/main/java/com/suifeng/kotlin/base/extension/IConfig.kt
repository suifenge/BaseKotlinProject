package com.suifeng.kotlin.base.extension

/**
 * @author admin
 * @data 2018/4/25
 * @describe
 */

interface IConfig{
    companion object {
        //AutoLayout
        const val LAYOUT_LINEARLAYOUT = "LinearLayout"
        const val LAYOUT_FRAMELAYOUT = "FrameLayout"
        const val LAYOUT_RELATIVELAYOUT = "RelativeLayout"
        //SharedPreference
        const val SHARED_PREFERENCES_FILE_NAME = "Shared_Preferences_FILE_NAME"
        //dialog相关
        val PROGRESS_TAG = "PROGRESS_TAG"
        val HintDialog_TAG = "HintDialog_TAG"
        //网络请求加载框
        val NET_PROGRESS_TAG = "NET_PROGRESS_TAG"
    }
}
package com.suifeng.kotlin.base.permissions

data class Permission (var name: String,    //权限名字
                       var granted: Boolean,    //有没有获取
                       var shouldShowRequestPermissionRationale: Boolean)   //是否显示请求的理由
{
    override fun toString(): String {
        return "Permission(name='$name', granted=$granted, shouldShowRequestPermissionRationale=$shouldShowRequestPermissionRationale)"
    }
}
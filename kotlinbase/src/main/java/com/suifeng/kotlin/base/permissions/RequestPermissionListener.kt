package com.suifeng.kotlin.base.permissions

interface RequestPermissionListener {
    fun onPermissionResult(permissions: MutableList<Permission>)
}
package com.suifeng.kotlin.base.permissions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.suifeng.kotlin.base.utils.log.KLog
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PermissionManager private constructor(){

    companion object {
        private const val TAG = "PermissionFragment"

        private var instance: PermissionManager? = null
            get() {
                if(field == null) {
                    field = PermissionManager()
                }
                return field
            }

        fun getPermissionManager(): PermissionManager {
            return instance!!
        }
    }

    private fun findPermissionFragment(parent: FragmentActivity): Fragment? {
        return parent.supportFragmentManager.findFragmentByTag(TAG)
    }

    suspend fun requestPermissions(parent: FragmentActivity, permissions: Array<String>): MutableList<Permission> = suspendCoroutine { coroutine ->
        var permissionFragment = findPermissionFragment(parent)
        if(permissionFragment == null) {
            permissionFragment =PermissionFragment.newInstance(*permissions)
            parent.supportFragmentManager
                    .beginTransaction()
                    .add(permissionFragment, TAG)
                    .commitAllowingStateLoss()
            parent.supportFragmentManager.executePendingTransactions()
        } else {
            permissionFragment as PermissionFragment
        }
        permissionFragment.setRequestPermissionListener(object: RequestPermissionListener{
            override fun onPermissionResult(permissions: MutableList<Permission>) {
                coroutine.resume(permissions)
            }

        })
    }
}
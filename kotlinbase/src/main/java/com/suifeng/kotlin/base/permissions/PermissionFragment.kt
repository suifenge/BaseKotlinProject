package com.suifeng.kotlin.base.permissions

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionFragment : Fragment() {

    private val permissions: Array<String> by lazy {
        arguments?.getStringArray(ARGS_PERMISSIONS) as Array<String>
    }

    private var mListener: RequestPermissionListener? = null

    companion object {
        private const val ARGS_PERMISSIONS = "ARGS_PERMISSIONS"
        private const val PERMISSIONS_REQUEST_CODE = 42

        fun newInstance(vararg permissions: String): PermissionFragment {
            val bundle = Bundle()
            bundle.putStringArray(ARGS_PERMISSIONS, permissions)
            val fragment = PermissionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        doRequestPermission()
    }

    private fun doRequestPermission() {
        if(permissions != null && permissions.isNotEmpty()) {
            if(hasPermissions(permissions)) {
                removeFragment()
                return
            }
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
        } else {
            removeFragment()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(PERMISSIONS_REQUEST_CODE == requestCode) {
            val mPermissions = mutableListOf<Permission>()
            removeFragment()
            permissions.forEachIndexed { index, s ->
                val permission = Permission(s, grantResults[index] == PackageManager.PERMISSION_GRANTED,
                        shouldShowRequestPermissionRationale(s))
                mPermissions.add(permission)
            }
            mListener?.onPermissionResult(mPermissions)
        }
    }

    public fun setRequestPermissionListener(listener: RequestPermissionListener) {
        this.mListener = listener
    }

    /**
     * 查看多个权限是否被允许
     */
    fun hasPermissions(permissions: Array<String>): Boolean {
        permissions.forEach {
            if(!isGranted(it)) {
                return false
            }
        }
        return true
    }

    /**
     * 查看单个权限是否被允许
     */
    private fun isGranted(permission: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if(ContextCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun removeFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
    }
}
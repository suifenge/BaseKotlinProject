package com.suifeng.kotlin.base.extension

import android.content.Context
import com.google.gson.Gson
import com.suifeng.kotlin.base.extension.IConfig.Companion.SHARED_PREFERENCES_FILE_NAME


/**
 * @author admin
 * @data 2018/4/26
 * @describe Shared使用
 */
inline fun <T> Context.SharedPut(
        key: String,
        value: T
) {
    val sp = getSharedPreferences(IConfig.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    val editor = sp.edit()
    when (value) {
        null -> editor.remove(key)
        is String -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Any -> editor.putString(key, Gson().toJson(value))
    }
    editor.apply()
}

inline fun Context.SharedGetString(key: String, default: String = ""): String {
    val sp = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getString(key, default)
}

inline fun Context.SharedGetInt(key: String, default: Int = 0): Int {
    val sp = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getInt(key, default)
}

inline fun Context.SharedGetBoolean(key: String, default: Boolean = false): Boolean {
    val sp = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getBoolean(key, default)
}

inline fun <T> Context.SharedGetAny(key: String, cls: Class<T>): T? {
    val sp = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    val value = sp.getString(key, null)
    return if (value != null && value.isNotEmpty()) {
        return Gson().fromJson(value, cls)
    } else {
        null
    }
}
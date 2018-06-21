package com.suifeng.kotlin.base.extension

import android.text.TextUtils.isEmpty
import com.suifeng.kotlin.base.utils.other.IDCardFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


/**
 * @author admin
 * @data 2018/4/27
 * @describe
 */

/**
 * 字符串去尾号0处理
 *
 * @param value    值
 * @param newValue 保留几位
 * @return
 */

/**
 * 是否为数字
 */
public inline fun String.isNumber(): Boolean {
    return if (this.isNotEmpty()) {
        val p = Pattern.compile("[0-9]*")
        val m = p.matcher(this)
        m.matches()
    } else {
        false
    }
}

/** 是否为double类型 */
public inline fun String.isDouble(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    val p = Pattern.compile("[0-9]*.[0-9]*")
    val m = p.matcher(this)
    return m.matches()
}

/** 是否为手机号码 */
public inline fun String.isMobile(): Boolean {
    val p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$") // 验证手机号
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 为身份张格式
 */
public inline fun String.isIDCard(): Boolean {
    return !this.isNotIDCard()
}

/**
 * 非身份张格式
 */
public inline fun String.isNotIDCard(): Boolean {
    val validate = IDCardFormat.IDCardValidate(this)
    return !isEmpty(validate)
}

/**
 * 是否为密码
 */
public inline fun String.isPassWord(): Boolean {
    val p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$")
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 判断邮箱是否合法
 */
public inline fun String.isEmail(): Boolean {
    if ("" == this) return false
    val p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")//复杂匹配
    val m = p.matcher(this)
    return m.matches()
}

inline fun Double.convertMoney(): String {
    val numberFormat = NumberFormat.getIntegerInstance(Locale.CHINA)
    return numberFormat.format(this)
}
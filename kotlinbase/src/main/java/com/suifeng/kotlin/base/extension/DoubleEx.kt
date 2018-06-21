package com.suifeng.kotlin.base.extension

import java.text.NumberFormat


/**
 * @author admin
 * @data 2018/4/27
 * @describe
 */

/**
 * Double转String
 * @param value    值
 * @param newValue 保留几位
 * @return
 */
public inline fun Double.toNumberFormat(value: Double, newValue: Int = 2): String {
    val nf = NumberFormat.getInstance()
    nf.maximumFractionDigits = newValue
    var replace = nf.format(value).replace(",", "")
    if (replace.endsWith(".0")) {
        replace = replace.substring(0, replace.length - 2)
    }
    return replace
}

/**
 * Float转String
 * @param value    值
 * @param newValue 保留几位
 * @return
 */
public inline fun Float.toNumberFormat(value: Float, newValue: Int = 2): String {
    val nf = NumberFormat.getInstance()
    nf.maximumFractionDigits = newValue
    var replace = nf.format(value).replace(",", "")
    if (replace.endsWith(".0")) {
        replace = replace.substring(0, replace.length - 2)
    }
    return replace
}

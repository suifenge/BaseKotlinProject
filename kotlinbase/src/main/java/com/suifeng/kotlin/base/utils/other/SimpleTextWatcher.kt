package com.suifeng.kotlin.base.utils.other

import android.text.Editable
import android.text.TextWatcher

/**
 * @author: ydm
 * @time: 2018/5/3
 * @说明:
 */
abstract class SimpleTextWatcher: TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

}
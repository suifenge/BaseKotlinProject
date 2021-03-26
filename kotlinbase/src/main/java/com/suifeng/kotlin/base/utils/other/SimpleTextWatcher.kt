package com.suifeng.kotlin.base.utils.other

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher: TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

}
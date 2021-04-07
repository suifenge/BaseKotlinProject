package com.suifeng.kotlin.base.widget.other

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 滚动文字
 */
class MarqueTextView : AppCompatTextView {


    private val mSb = StringBuilder()
    var isInit = true

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    override fun isFocused(): Boolean {
        return true
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {

    }

    override fun onDraw(canvas: Canvas) {
        val sb = mSb
        if (isInit) {
            val length = sb.length
            if (length > 0) {
                sb.delete(0, length)
            }
            val paint = paint
            sb.append(text)
            val textLength = paint.measureText(sb.toString())
            val needWidth = width - textLength
            if (needWidth >= 0) {
                val spaceWidth = paint.measureText(" ")
                val number = (needWidth / spaceWidth + 0.5f).toInt() + 2
                for (i in 0 until number) {
                    sb.append(" ")
                }
                text = sb.toString()
            }
            isInit = false
        }
        super.onDraw(canvas)
    }
}
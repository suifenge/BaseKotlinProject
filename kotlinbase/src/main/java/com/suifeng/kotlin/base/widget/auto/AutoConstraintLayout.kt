package com.suifeng.kotlin.base.widget.auto

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.ViewGroup
import com.zhy.autolayout.AutoLayoutInfo
import com.zhy.autolayout.utils.AutoLayoutHelper

/**
 * @atuthor ydm
 * @data on 2018/8/8
 * @describe
 */
class AutoConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private val mHelper = AutoLayoutHelper(this)

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!isInEditMode) {
            mHelper.adjustChildren()
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    class LayoutParams : ConstraintLayout.LayoutParams, AutoLayoutHelper.AutoLayoutParams {
        private var mAutoLayoutInfo: AutoLayoutInfo? = null

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs)
        }

        constructor(width: Int, height: Int) : super(width, height)


        constructor(source: ViewGroup.LayoutParams) : super(source)

        constructor(source: ViewGroup.MarginLayoutParams) : super(source)


        constructor(source: LayoutParams) : this(source as ConstraintLayout.LayoutParams) {
            mAutoLayoutInfo = source.mAutoLayoutInfo
        }

        override fun getAutoLayoutInfo(): AutoLayoutInfo? {
            return mAutoLayoutInfo
        }


    }

}
package com.suifeng.kotlin.base.widget.recyclerview

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics

/**
 * 可控制速度的  的 LinearLayoutManager
 */

class ScrollSpeedLinearLayoutManger(context: Context) : LinearLayoutManager(context) {

    companion object {
        const val MILLISECONDS_PER_INCH = 3f
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@ScrollSpeedLinearLayoutManger
                        .computeScrollVectorForPosition(targetPosition)
            }

            //This returns the milliseconds it takes to
            //scroll one pixel.
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.density
                //返回滑动一个pixel需要多少毫秒
            }

        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

}
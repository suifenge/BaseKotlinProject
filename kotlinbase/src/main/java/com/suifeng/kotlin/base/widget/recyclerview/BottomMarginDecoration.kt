package com.suifeng.kotlin.base.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @desc 往RecyclerView底部添加一些marginBottom
 */
class BottomMarginDecoration(private val marginBottom: Int) : RecyclerView.ItemDecoration() {

    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is LinearLayoutManager) {
            spanCount = 1
        }
        return spanCount
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        val adapter = parent.adapter
        var childCount = adapter!!.itemCount
        if (layoutManager is GridLayoutManager) {
            childCount -= childCount % spanCount
            if (pos >= childCount) {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == 1) {
                childCount -= childCount % spanCount
                if (pos >= childCount) {
                    return true
                }
            } else if ((pos + 1) % spanCount == 0) {
                return true
            }
        } else if (layoutManager is LinearLayoutManager) {
            if (pos == childCount - 1) {
                return true
            }
        }
        return false
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val isLast = isLastRaw(parent, (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition, this.getSpanCount(parent))
        if (isLast) {
            outRect.bottom = marginBottom
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}

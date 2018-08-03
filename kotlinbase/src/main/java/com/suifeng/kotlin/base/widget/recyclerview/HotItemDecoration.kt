/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.suifeng.kotlin.base.widget.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

import com.suifeng.kotlin.base.R


/**
 * <pre>
 * mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 * mLayoutManager.getOrientation());
 * recyclerView.addItemDecoration(mDividerItemDecoration);
</pre> *
 */
class HotItemDecoration
/**
 * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
 *
 * @param context Current context, it will be used to access resources.
 */
(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    private val mBounds = Rect()

    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.gray_line)
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + mDivider!!.intrinsicWidth)
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

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

    /**
     * 是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @return
     */
    private fun isLastCol(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        val adapter = parent.adapter
        var childCount = adapter.itemCount
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.HORIZONTAL) {
                childCount -= childCount % spanCount
                if (pos >= childCount) {
                    return true
                }
            } else if ((pos + 1) % spanCount == 0) {
                return true
            }
        } else if (layoutManager is LinearLayoutManager) {
            return true
        }
        return false
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        val adapter = parent.adapter
        var childCount = adapter.itemCount
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

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        val pos = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (isLastRaw(parent, pos, getSpanCount(parent))) {
            outRect.set(0, 0, mDivider!!.intrinsicHeight, 0)
        } else if (isLastCol(parent, pos, this.getSpanCount(parent))) {
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
        }
    }
}

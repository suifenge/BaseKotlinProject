package com.suifeng.kotlin.base.widget.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author: robert
 * @date: 2017-12-05
 * @time: 14:51
 * @说明: 往RecyclerView底部添加一些marginBottom
 */
public class BottomMarginDecoration extends RecyclerView.ItemDecoration {

    private final int marginBottom;

    public BottomMarginDecoration(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        }
        return spanCount;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        RecyclerView.Adapter adapter = parent.getAdapter();
        int childCount = adapter.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            childCount -= childCount % spanCount;
            if (pos >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == 1) {
                childCount -= childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            } else if ((pos + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (pos == (childCount - 1)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        boolean isLast = isLastRaw(parent, ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), this.getSpanCount(parent));
        if (isLast) {
            outRect.bottom = marginBottom;
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}

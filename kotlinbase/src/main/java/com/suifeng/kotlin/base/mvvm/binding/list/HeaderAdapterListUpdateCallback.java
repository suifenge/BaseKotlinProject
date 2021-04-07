package com.suifeng.kotlin.base.mvvm.binding.list;

import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.suifeng.kotlin.base.adapter.BaseBindingAdapter;

public class HeaderAdapterListUpdateCallback implements ListUpdateCallback {
    private RecyclerView.Adapter mAdapter;

    public HeaderAdapterListUpdateCallback(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void onInserted(int position, int count) {
        mAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        if (isHeadAdapter() && position == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRangeRemoved(position, count);
        }
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        if (isHeadAdapter() && position == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRangeChanged(position, count, payload);
        }
    }

    /**
     * 判断是不是带头部的适配器，如果有头部不能局部操作头部Item，否则会报错
     *
     * @return
     */
    private boolean isHeadAdapter() {
        if (mAdapter instanceof BaseBindingAdapter) {
            return true;
        }
        return false;
    }
}

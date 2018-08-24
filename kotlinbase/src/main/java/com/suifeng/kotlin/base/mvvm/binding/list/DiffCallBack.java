package com.suifeng.kotlin.base.mvvm.binding.list;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * 增加更新数据对比工具
 */
public class DiffCallBack extends DiffUtil.Callback {
    List<Object> mOldList;
    List<Object> mNewList;

    public DiffCallBack(List<Object> oldList, List<Object> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldObj = mOldList.get(oldItemPosition);
        Object newObj = mNewList.get(newItemPosition);
        return oldObj.getClass().equals(newObj.getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldObj = mOldList.get(oldItemPosition);
        Object newObj = mNewList.get(newItemPosition);
        return oldObj.equals(newObj);
    }
}

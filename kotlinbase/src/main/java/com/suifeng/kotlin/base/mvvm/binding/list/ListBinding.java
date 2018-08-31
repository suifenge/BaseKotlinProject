package com.suifeng.kotlin.base.mvvm.binding.list;

import android.databinding.BindingAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.suifeng.kotlin.base.adapter.BaseBindingAdapter;
import com.suifeng.kotlin.base.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表布局的相关绑定适配
 * Created by Hzz on 2018/8/20
 */
public class ListBinding {

    @BindingAdapter(value = "data")
    public static void bindRecyclerViewData(RecyclerView recyclerView, List data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        bindAdapterData(adapter, data);
    }

    /**
     * 数据更新到适配器
     *
     * @param adapter
     * @param data    刷新的数据
     */
    private static void bindAdapterData(RecyclerView.Adapter adapter, List data) {
        if (adapter instanceof BaseBindingAdapter) {
            BaseBindingAdapter adt = (BaseBindingAdapter) adapter;
            ArrayList<Object> oldData = adt.getList();
            diffBindAdapterData(adapter, oldData, data);
        } else if (adapter instanceof CommonAdapter) {
            CommonAdapter adt = (CommonAdapter) adapter;
            ArrayList oldData = adt.getData();
            diffBindAdapterData(adapter, oldData, data);
        }
    }

    /**
     * 增量更新数据集并刷新适配器
     *
     * @param adapter
     * @param oldDatas 旧数据
     * @param newDatas 新数据
     */
    private static void diffBindAdapterData(RecyclerView.Adapter adapter, List oldDatas, List newDatas) {
        if(oldDatas.isEmpty()){
            oldDatas.addAll(newDatas);
            adapter.notifyDataSetChanged();
        }else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldDatas, newDatas));
            oldDatas.clear();
            oldDatas.addAll(newDatas);
            diffResult.dispatchUpdatesTo(new HeaderAdapterListUpdateCallback(adapter));
        }
    }
}

package com.suifeng.kotlin.base.adapter

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ljc
 * @data 2018/6/22
 * @describe
 */
class BaseViewHolder : RecyclerView.ViewHolder {

    private val views = SparseArray<View>()

    constructor(itemView: View) : super(itemView)

    @Suppress("UNCHECKED_CAST")
    public fun <T : View> get(viewId: Int) : T {
        var view = views[viewId]
        if(view == null) {
            view = itemView.findViewById<View>(viewId)
            views.put(viewId, view)
        }
        return view as T
    }
}
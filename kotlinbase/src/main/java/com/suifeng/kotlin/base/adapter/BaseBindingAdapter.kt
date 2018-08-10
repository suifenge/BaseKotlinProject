package com.suifeng.kotlin.base.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author ljc
 * @data 2018/8/10
 * @describe
 */
abstract class BaseBindingAdapter<T>(
        context: Context,
        private val variableId: Int,
        private val layoutId: Int,
        private val list: ArrayList<T>,
        //Item点击监听回调
        private var itemClickListener: BaseBindingAdapter.OnItemClickListener<T>? = null,
        //点击View的Id
        private val clickIds: ArrayList<Int>? = null
): RecyclerView.Adapter<BaseBindingViewHolder>() {

    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        //绑定监听回调
        initializationItemClickListener(binding.root)
        // 创建
        return BaseBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        holder.binding.setVariable(variableId, list[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     *  初始化Item 回调
     *  @param inflate   绑定的布局
     */
    private fun initializationItemClickListener(inflate: View) {
        if (clickIds == null) {
            // 给 root item 设置监听
            inflate.setOnClickListener {
                clickCallback(inflate, it)
            }
        } else {
            // 给 item 上面的 子 view 设置监听
            clickIds.forEach {
                inflate.findViewById<View>(it)?.setOnClickListener {
                    clickCallback(inflate, it)
                }
            }
        }
    }

    /** 监听到点击事件后，回调 */
    private fun clickCallback(inflate: View, it: View) {
        val pos = inflate.tag
        if (pos is Int) {
            //回调
            itemClickListener?.click(it, this, list[pos], pos)
        } else {
            Exception("root item 请勿使用setTag操作,影响逻辑")
        }
    }


    /**
     * item点击监听
     */
    interface OnItemClickListener<T> {
        /**
         *  点击的时候回调
         *  @param view      点击的View
         *  @param adapter   当前的adapter
         *  @param bean      获取到的数据结构
         *  @param position  点击的item
         */
        fun click(view: View, adapter: BaseBindingAdapter<T>, bean: T, position: Int)
    }
}
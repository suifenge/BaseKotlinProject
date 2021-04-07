package com.suifeng.kotlin.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ljc
 * @data 2018/6/22
 * @describe 没有使用DataBinding来进行对Adapter封装
 */
abstract class CommonAdapter<T>(
        @LayoutRes private val layoutId: Int,
        @NonNull var data: ArrayList<T>) : RecyclerView.Adapter<BaseViewHolder>() {
    /* 监听列表 id */
    private val mClickIDs = ArrayList<Int>()
    /* 简单功能监听 */
    private var mSimpleItemClickListener: OnSimpleItemClickListener? = null
    /* 多功能监听 */
    private var mItemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        //简单监听
        setSimpleItemClick(inflate)
        //复杂监听
        setItemClick(inflate)
        //返回
        return BaseViewHolder(inflate)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //设置监听的Position
        holder.itemView.tag = position
        //回调
        val t = data.get(position)
        convert(holder, t, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    /**
     * 为Item设置简单监听事件
     */
    private fun setSimpleItemClick(inflate: View) {
        if (mClickIDs.size > 0 && mSimpleItemClickListener != null) {
            for (id in mClickIDs) {
                val viewById = inflate.findViewById<View>(id)
                if (viewById == null) {
                    Log.e("tag", "error not findViewById")
                    continue
                }
                itemClick(inflate, viewById)
            }
        } else {
            itemClick(inflate, inflate)
        }
    }

    /**
     * 为Item设置简单监听事件
     */
    private fun setItemClick(inflate: View) {
        if (mClickIDs.size > 0 && mItemClickListener != null) {
            for (id in mClickIDs) {
                val viewById = inflate.findViewById<View>(id)
                if (viewById == null) {
                    Log.e("tag", "error not findViewById")
                    continue
                }
                itemClick(inflate, viewById)
            }
        } else {
            itemClick(inflate, inflate)
        }
    }

    /**
     * Item Click 会调
     */
    private fun itemClick(inflate: View, child: View) {
        if (mSimpleItemClickListener == null && mItemClickListener == null) return
        child.setOnClickListener { v ->
            val tag = inflate.tag
            if (tag is Int) {
                //简单回调存在时
                if (mSimpleItemClickListener != null) {
                    mSimpleItemClickListener!!.onItemChildClick(v, tag)
                } else {
                    val t = data[tag]
                    mItemClickListener!!.onItemChildClick(v, this@CommonAdapter, t, tag)
                }

            } else {
                Log.e("tag", "inflate 请勿使用setTag操作,影响逻辑")
            }
        }
    }

    /**
     * 监听事件
     */
    interface OnSimpleItemClickListener {
        fun onItemChildClick(v: View, position: Int)
    }

    /**
     * 多功能监听
     *
     * @param <T>
    </T> */
    interface OnItemClickListener<T> {
        fun onItemChildClick(v: View, adapter: CommonAdapter<T>, bean: T, position: Int)
    }

    /* 设置监听事件 */
    fun setOnSimpleItemClickListener(onSimpleItemClickListener: OnSimpleItemClickListener, vararg viewIds: Int) {
        viewIds.forEach { mClickIDs.add(it) }
        mSimpleItemClickListener = onSimpleItemClickListener
    }

    /* 设置监听事件 */
    fun setOnItemClickListener(onClickListener: OnItemClickListener<T>, vararg viewIds: Int) {
        viewIds.forEach { mClickIDs.add(it) }
        mItemClickListener = onClickListener
    }

    /* 抽象数据方法，简化Adapter */
    protected abstract fun convert(holder: BaseViewHolder, bean: T, position: Int)
}
package com.suifeng.kotlin.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

/**
 * 简单对DataBinding进行封装的adapter
 */
abstract class BaseBindingAdapter<T, B : ViewDataBinding>(
        context: Context,
        private val layoutID: Int,
        val list: ArrayList<T> = ArrayList(),
        // Item点击监听回调
        var itemClickListener: OnItemClickListener<T>? = null,
        // layout 上 绑定的监听id
        vararg var clickIDs: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = -100
        const val VIEW_TYPE_FOOTER = -101
    }

    private var mHeaderLayout: LinearLayout? = null
    private var mFooterLayout: LinearLayout? = null

    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    open fun getLayoutId(viewType: Int): Int {
        return layoutID//默认返回传入的layout，可重写
    }

    override fun getItemViewType(position: Int): Int {
        mHeaderLayout?.let {
            if (position == 0) return VIEW_TYPE_HEADER
        }

        mFooterLayout?.let {
            if (position == getHeaderLayoutCount() + list.size) return VIEW_TYPE_FOOTER
        }
        return getDefItemViewType(position - getHeaderLayoutCount())//取在数据中的下标
    }

    protected fun getDefItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                createBaseViewHolder(parent, viewType, mHeaderLayout!!)
            }
            VIEW_TYPE_FOOTER -> {
                createBaseViewHolder(parent, viewType, mFooterLayout!!)
            }
            else -> {
                createBindingViewHolder(parent, viewType)
            }
        }
    }

    private fun createBaseViewHolder(parent: ViewGroup, viewType: Int, itemView: View): BaseViewHolder {
        return BaseViewHolder(itemView)
    }

    private fun createBindingViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<B> {
        val layoutId = getLayoutId(viewType)
        val binding = DataBindingUtil.inflate<B>(layoutInflater, layoutId, parent, false)
        // 绑定监听回调
        initializationItemClickListener(binding.root)
        // 创建
        return BaseBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            VIEW_TYPE_HEADER -> {
            }//undo
            VIEW_TYPE_FOOTER -> {
            }//undo
            else -> {
                //实际上绑定数据的ViewHolder
                onBindBindingViewHolder(holder as BaseBindingViewHolder<B>, position - getHeaderLayoutCount())
            }
        }

    }

    protected fun onBindBindingViewHolder(holder: BaseBindingViewHolder<B>, position: Int) {
        holder.binding.root.tag = position
        val bean = list[position]
        convert(holder, bean, position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size + getHeaderLayoutCount() + getFooterLayoutCount()
    }

    /**
     * RecyclerView需要先设置manager再设置adapter才有效
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = getItemViewType(position)
                    if (isFixedViewType(viewType)) {
                        return layoutManager.spanCount
                    }
                    return 1
                }
            }
        }
    }

    private fun isFixedViewType(viewType: Int): Boolean {
        return when (viewType) {
            VIEW_TYPE_HEADER,
            VIEW_TYPE_FOOTER -> true
            else -> false
        }
    }

    /**
     *  初始化Item 回调
     *  @param inflate   绑定的布局
     */
    private fun initializationItemClickListener(inflate: View) {
        if (clickIDs.isEmpty()) {
            // 给 root item 设置监听
            inflate.setOnClickListener {
                clickCallback(inflate, it)
            }
        } else {
            // 给 item 上面的 子 view 设置监听
            clickIDs.forEach {
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
            throw Exception("root item 请勿使用setTag操作,影响逻辑")
        }
    }

    protected abstract fun convert(holder: BaseBindingViewHolder<B>, bean: T, position: Int)


    /**
     * 添加头部
     * @param index
     */
    fun addHeader(view: View, index: Int = -1): Int {
        if (mHeaderLayout == null) {
            mHeaderLayout = LinearLayout(view.context)
            mHeaderLayout?.orientation = LinearLayout.VERTICAL
            mHeaderLayout?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        var insertIndex = 0
        val childCount = mHeaderLayout!!.childCount
        if (index < 0 || index > childCount) {
            insertIndex = childCount
        }

        mHeaderLayout!!.addView(view, insertIndex)
        if (mHeaderLayout!!.childCount == 1) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return insertIndex
    }

    /**
     * 添加尾部
     */
    fun addFooter(view: View, index: Int = -1): Int {
        if (mFooterLayout == null) {
            mFooterLayout = LinearLayout(view.context)
            mFooterLayout?.orientation = LinearLayout.VERTICAL
            mFooterLayout?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        var insertIndex = 0
        val childCount = mFooterLayout!!.childCount
        if (index < 0 || index > childCount) {
            insertIndex = childCount
        }

        mFooterLayout!!.addView(view, insertIndex)
        if (mFooterLayout!!.childCount == 1) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return insertIndex
    }

    fun removeHeader(view: View) {
        mHeaderLayout?.let {
            it.removeView(view)
            if (it.childCount == 0) {
                val position = getHeaderViewPosition()
                if (position != -1) {
                    notifyItemRemoved(position)
                }
            }
        }
    }

    fun removeFooter(view: View) {
        mFooterLayout?.let {
            it.removeView(view)
            if (it.childCount == 0) {
                val position = getFooterViewPosition()
                if (position != -1) {
                    notifyItemRemoved(position)
                }
            }
        }
    }

    fun getHeaderLayoutCount(): Int {
        return mHeaderLayout?.let {
            if (getHeaderCount() > 0) 1 else 0
        } ?: 0
    }

    fun getFooterLayoutCount(): Int {
        return mFooterLayout?.let {
            if (getFooterCount() > 0) 1 else 0
        } ?: 0
    }

    protected fun getHeaderCount(): Int {
        return mHeaderLayout?.childCount ?: 0
    }

    protected fun getFooterCount(): Int {
        return mFooterLayout?.childCount ?: 0
    }

    fun getHeaderViewPosition(): Int {
        mHeaderLayout?.let { return 0 }
        return -1
    }

    fun getFooterViewPosition(): Int {
        mFooterLayout?.let { return getHeaderCount() + list.size }
        return -1
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
        fun click(view: View, adapter: BaseBindingAdapter<T, *>, bean: T, position: Int)
    }
}


package com.suifeng.kotlin.base.widget.loading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author ljc
 * @data 2018/6/28
 * @describe
 */
class VaryViewHelper(view: View) : IVaryViewHelper {

    private val mView : View = view
    private var parentView: ViewGroup ? = null
    private var viewIndex = -1
    private var params: ViewGroup.LayoutParams ? = null
    private var currentView: View ? = null

    override fun getCurrentLayout() = currentView

    override fun restoreView() {
        showLayout(mView)
    }

    override fun showLayout(view: View) {
        if(parentView == null) {
            initView()
        }
        this.currentView = view
        // 如果已经是那个view，那就不需要再进行替换操作了
        if(parentView?.getChildAt(viewIndex) != mView) {
            val parent : ViewGroup = mView.parent as ViewGroup
            parent.removeView(mView)
            parentView?.removeViewAt(viewIndex)
            parentView?.addView(mView, viewIndex, params)
        }
    }

    private fun initView() {
        params = mView.layoutParams
        parentView = if(mView.parent != null) {
            mView.parent as ViewGroup
        } else {
            mView.rootView.findViewById(android.R.id.content)
        }
        val count = parentView?.childCount
        for (index in 0 until (count ?: 0)) {
            if(mView == parentView?.getChildAt(index)) {
                viewIndex = index
                break
            }
        }
        currentView = mView
    }

    override fun inflate(layoutId: Int): View = LayoutInflater.from(mView.context).inflate(layoutId, null)

    override fun getContext(): Context = mView.context

    override fun getView(): View = mView
}
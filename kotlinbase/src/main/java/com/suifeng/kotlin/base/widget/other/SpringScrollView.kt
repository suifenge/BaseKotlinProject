package com.suifeng.kotlin.base.widget.other

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ScrollView

/**
 * 带弹性滑动的ScrollView
 */
class SpringScrollView : ScrollView {
    // 手指按下时的Y值, 用于在移动时计算移动距离,如果按下时不能上拉和下拉，会在手指移动时更新为当前手指的Y值
    private var startY: Float = 0.toFloat()
    // ScrollView的唯一内容控件
    private var contentView: View? = null
    // 用于记录正常的布局位置
    private val originalRect = Rect()
    // 是否可以继续下拉
    private var canPullDown = false
    // 是否可以继续上拉
    private var canPullUp = false
    // 记录是否移动了布局
    private var isMoved = false


    /**
     * 判断是否滚动到顶部
     */
    private val isCanPullDown: Boolean
        get() = scrollY == 0 || contentView!!.height < height + scrollY


    /**
     * 判断是否滚动到底部
     */
    private val isCanPullUp: Boolean
        get() = contentView!!.height <= height + scrollY

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    @SuppressLint("MissingSuperCall")
    override fun onFinishInflate() {
        if (childCount > 0) {
            contentView = getChildAt(0)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (contentView == null) {
            return
        }
        // ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
        originalRect.set(contentView!!.left, contentView!!.top, contentView!!.right,
                contentView!!.bottom)
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (contentView == null) {
            return super.dispatchTouchEvent(ev)
        }
        // 手指是否移动到了当前ScrollView控件之外
        val isTouchOutOfScrollView = ev.y >= this.height || ev.y <= 0
        // 如果移动到了当前ScrollView控件之外
        if (isTouchOutOfScrollView) {
            // 如果当前contentView已经被移动, 首先把布局移到原位置, 然后消费点这个事件
            if (isMoved) {
                boundBack()
            }
            return true
        }
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // 判断是否可以上拉和下拉
                canPullDown = isCanPullDown
                canPullUp = isCanPullUp
                // 记录按下时的Y值
                startY = ev.y
            }
            MotionEvent.ACTION_UP -> boundBack()
            MotionEvent.ACTION_MOVE -> {
                // 在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.y
                    canPullDown = isCanPullDown
                    canPullUp = isCanPullUp
                }
                // 计算手指移动的距离
                val nowY = ev.y
                val deltaY = (nowY - startY).toInt()

                // 是否应该移动布局
                val shouldMove = (canPullDown && deltaY > 0 // 可以下拉， 并且手指向下移动

                        || canPullUp && deltaY < 0 // 可以上拉， 并且手指向上移动

                        || canPullUp && canPullDown) // 既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                if (shouldMove) {
                    // 计算偏移量
                    val offset = (deltaY * MOVE_FACTOR).toInt()
                    // 随着手指的移动而移动布局
                    contentView!!.layout(originalRect.left, originalRect.top + offset, originalRect.right,
                            originalRect.bottom + offset)
                    isMoved = true // 记录移动了布局
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    /**
     * 将内容布局移动到原位置 可以在UP事件中调用, 也可以在其他需要的地方调用, 如手指移动到当前ScrollView外时
     */
    private fun boundBack() {
        if (!isMoved) {
            return  // 如果没有移动布局， 则跳过执行
        }
        // 开启动画
        val anim = TranslateAnimation(0f, 0f, contentView!!.top.toFloat(), originalRect.top.toFloat())
        anim.duration = ANIM_TIME.toLong()
        contentView!!.startAnimation(anim)
        // 设置回到正常的布局位置
        contentView!!.layout(originalRect.left, originalRect.top, originalRect.right,
                originalRect.bottom)
        // 将标志位设回false
        canPullDown = false
        canPullUp = false
        isMoved = false
    }

    companion object {
        // 移动因子,手指移动100px,那么View就只移动50px
        private val MOVE_FACTOR = 0.5f
        // 松开手指后, 界面回到正常位置需要的动画时间
        private val ANIM_TIME = 300
    }
}
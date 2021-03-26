package com.suifeng.kotlin.base.widget.dialog.password

import android.content.Context
import android.graphics.*
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.suifeng.kotlin.base.R
import java.util.*

class PayPwdView : View {

    private var result: ArrayList<String>? = null//输入结果保存
    private var count: Int = 0//密码位数
    private var size: Int = 0//默认每一格的大小
    private var mBorderPaint: Paint? = null//边界画笔
    private var mDotPaint: Paint? = null//掩盖点的画笔
    private var mBorderColor: Int = 0//边界颜色
    private var mDotColor: Int = 0//掩盖点的颜色
    private var mRoundRect: RectF? = null//外面的圆角矩形
    private var mRoundRadius: Int = 0//圆角矩形的圆角程度

    private var inputCallBack: InputCallBack? = null//输入完成的回调
    private var inputMethodView: PwdInputMethodView? = null //输入键盘

    /**
     * 获取输入文字
     *
     * @return
     */
    val inputText: String?
        get() {
            if (result!!.size == count) {
                val sb = StringBuffer()
                for (i in result!!) {
                    sb.append(i)
                }
                return sb.toString()
            }
            return null
        }

    constructor(context: Context) : super(context) {
        init(null)
    }


    interface InputCallBack {
        fun onInputFinish(result: String, tag: String)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    /**
     * 初始化相关参数
     */
    internal fun init(attrs: AttributeSet?) {
        val dp = resources.displayMetrics.density
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        result = ArrayList()
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PayPwdView)
            mBorderColor = ta.getColor(R.styleable.PayPwdView_pay_border_color, Color.LTGRAY)
            mDotColor = ta.getColor(R.styleable.PayPwdView_dot_color, Color.BLACK)
            count = ta.getInt(R.styleable.PayPwdView_count, 4)
            ta.recycle()
        } else {
            mBorderColor = Color.LTGRAY
            mDotColor = Color.GRAY
            count = 4//默认6位密码
        }
        size = (dp * 30).toInt()//默认30dp一格
        //color
        mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBorderPaint!!.strokeWidth = 3f
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.color = mBorderColor

        mDotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mDotPaint!!.strokeWidth = 3f
        mDotPaint!!.style = Paint.Style.FILL
        mDotPaint!!.color = mDotColor
        mRoundRect = RectF()
        mRoundRadius = (5 * dp).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = measureWidth(widthMeasureSpec)
        var h = measureHeight(heightMeasureSpec)
        val wsize = View.MeasureSpec.getSize(widthMeasureSpec)
        val hsize = View.MeasureSpec.getSize(heightMeasureSpec)
        //宽度没指定,但高度指定
        if (w == -1) {
            if (h != -1) {
                w = h * count//宽度=高*数量
                size = h
            } else {//两个都不知道,默认宽高
                w = size * count
                h = size
            }
        } else {//宽度已知
            if (h == -1) {//高度不知道
                h = w / count
                size = h
            }
        }
        setMeasuredDimension(Math.min(w, wsize), Math.min(h, hsize))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        //宽度
        val wmode = View.MeasureSpec.getMode(widthMeasureSpec)
        val wsize = View.MeasureSpec.getSize(widthMeasureSpec)
        return if (wmode == View.MeasureSpec.AT_MOST) {//wrap_content
            -1
        } else wsize
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        //高度
        val hmode = View.MeasureSpec.getMode(heightMeasureSpec)
        val hsize = View.MeasureSpec.getSize(heightMeasureSpec)
        return if (hmode == View.MeasureSpec.AT_MOST) {//wrap_content
            -1
        } else hsize
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {//点击控件弹出输入键盘
            requestFocus()
            inputMethodView!!.visibility = View.VISIBLE
            return true
        }
        return true
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        if (gainFocus) {
            inputMethodView!!.visibility = View.VISIBLE
        } else {
            inputMethodView!!.visibility = View.GONE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width - 2
        val height = height - 2
        //先画个圆角矩形
        mRoundRect!!.set(0f, (height / 8).toFloat(), width.toFloat(), (height / 8 * 7).toFloat())
        canvas.drawRoundRect(mRoundRect!!, mRoundRadius.toFloat(), mRoundRadius.toFloat(), mBorderPaint!!)
        //画分割线
        for (i in 1 until count) {
            val x = i * size
            canvas.drawLine(x.toFloat(), (height / 8).toFloat(), x.toFloat(), (height / 8 * 7).toFloat(), mBorderPaint!!)
        }
        //画掩盖点,
        // 这是前面定义的变量 private ArrayList<Integer> result;//输入结果保存
        val dotRadius = size / 10//圆圈占格子的三分之一
        for (i in result!!.indices) {
            val x = (size * (i + 0.5)).toFloat()
            val y = (size / 2).toFloat()
            canvas.drawCircle(x, y, dotRadius.toFloat(), mDotPaint!!)
        }
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER//输入类型为数字
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE
        return MyInputConnection(this, false)
    }

    fun setInputCallBack(inputCallBack: InputCallBack) {
        this.inputCallBack = inputCallBack
    }

    fun clearResult() {
        result!!.clear()
        invalidate()
    }


    private inner class MyInputConnection(targetView: View, fullEditor: Boolean) : BaseInputConnection(targetView, fullEditor) {

        override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
            //这里是接受输入法的文本的，我们只处理数字，所以什么操作都不做
            return super.commitText(text, newCursorPosition)
        }

        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            //软键盘的删除键 DEL 无法直接监听，自己发送del事件
            return if (beforeLength == 1 && afterLength == 0) {
                super.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && super.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
            } else super.deleteSurroundingText(beforeLength, afterLength)
        }
    }


    /**
     * 设置输入键盘view
     *
     * @param inputMethodView
     */
    fun setInputMethodView(inputMethodView: PwdInputMethodView) {
        this.inputMethodView = inputMethodView
        this.inputMethodView!!.setInputReceiver(object : PwdInputMethodView.InputReceiver {
            override fun receive(num: String) {
                if (num == "-1") {
                    if (!result!!.isEmpty()) {
                        result!!.removeAt(result!!.size - 1)
                        invalidate()
                    }
                } else {
                    if (result!!.size < count) {
                        result!!.add(num)
                        invalidate()
                        ensureFinishInput()
                    }
                }
            }
        })
    }

    /**
     * 判断是否输入完成，输入完成后调用callback
     */
    internal fun ensureFinishInput() {
        if (result!!.size == count && inputCallBack != null) {//输入完成
            val sb = StringBuffer()
            for (i in result!!) {
                sb.append(i)
            }
            inputCallBack!!.onInputFinish(sb.toString(), tag as String)
        }
    }
}

package com.suifeng.kotlin.base.extension

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/**
 * 针对View动画的 扩展对象
 */

public inline fun View.rotate(endRotate: Float, duration: Long = 600) {
    val anim = ObjectAnimator.ofFloat(this, "rotation", this.rotation, endRotate)
    anim.duration = duration
    anim.start()
}

public inline fun View.rotate(startRotate: Float, endRotate: Float, duration: Long = 600) {
    val anim = ObjectAnimator.ofFloat(this, "rotation", startRotate, endRotate)
    anim.duration = duration
    anim.start()
}

/**
 * 大小变化
 * @param view
 */
fun View.scale(duration: Int) {
    val anim1 = ObjectAnimator.ofFloat(this, "scaleX", 0.5f, 1f)
    val anim2 = ObjectAnimator.ofFloat(this, "scaleY", 0.5f, 1f)
    val animSet = AnimatorSet()
    animSet.play(anim1).with(anim2)
    animSet.duration = duration.toLong()
    animSet.start()
}

/**
 * 大小变化
 * @param view
 */
fun View.scaleY(fromY: Float, toY: Float, duration: Int) {
    val anim = ObjectAnimator.ofFloat(this, "scaleY", fromY, toY)
    anim.duration = duration.toLong()
    anim.start()
}

/**
 * 大小变化
 * @param view
 */
fun View.scaleY(fromY: Float, toY: Float, duration: Int, listenerAdapter: AnimatorListenerAdapter) {
    val anim = ObjectAnimator.ofFloat(this, "scaleY", fromY, toY)
    anim.duration = duration.toLong()
    anim.addListener(listenerAdapter)
    anim.start()
}

package com.petsvote.ui.ext

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation

private val DEFAULT_ANIM_DURATION = 200L


fun View.hide(callback: (() -> Unit)? = null){
    collapse(this, callback = callback)
}

fun View.show(callback: (() -> Unit)? = null){
    expand(this, callback = callback)
}

fun collapse(
    view: View?,
    duration: Long = DEFAULT_ANIM_DURATION,
    callback: (() -> Unit)? = null
) {
    val initialHeight = view?.measuredHeight ?: 0

    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                view?.visibility = View.GONE
            } else {
                view?.layoutParams?.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                view?.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }.apply {
        this.interpolator = DecelerateInterpolator(2f)
        this.duration = duration
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                callback?.invoke()
            }
        })
    }

    view?.startAnimation(a)
}

fun expand(
    view: View?,
    duration: Long = DEFAULT_ANIM_DURATION,
    callback: (() -> Unit)? = null
) {
    view?.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = view?.measuredHeight ?: 0

    view?.layoutParams?.height = 0
    view?.visibility = View.VISIBLE
    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            view?.layoutParams?.height = if (interpolatedTime == 1f)
                ViewGroup.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            view?.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }.apply {
        this.interpolator = DecelerateInterpolator(2f)
        this.duration = duration
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                callback?.invoke()
            }
        })
    }
    view?.startAnimation(a)
}
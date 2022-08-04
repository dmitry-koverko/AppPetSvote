package com.petsvote.ui.ext

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import androidx.dynamicanimation.animation.DynamicAnimation

private val DEFAULT_ANIM_DURATION = 200L
private val PROPERTY_ALPHA = "ALPHA"


fun View.hide(callback: (() -> Unit)? = null){
    collapse(this, callback = callback)
}

fun View.show(callback: (() -> Unit)? = null){
    expand(this, callback = callback)
}

fun View.hideAlpha(callback: (() -> Unit)? = null){
    alphaHide(this, callback = callback)
}

fun View.showAlpha(callback: (() -> Unit)? = null){
    alphaShow(this, callback = callback)
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

fun alphaShow(
    view: View?,
    duration: Long = DEFAULT_ANIM_DURATION,
    callback: (() -> Unit)? = null
) {

    val propertyXLeft: PropertyValuesHolder =
        PropertyValuesHolder.ofFloat(PROPERTY_ALPHA, 0f, 1f)

    var animator = ValueAnimator()
    animator.setValues(propertyXLeft)
    animator.setDuration(duration)
    animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
        var alpha = animation.getAnimatedValue(PROPERTY_ALPHA) as Float
        view?.alpha = alpha
    })
    animator.addListener(object : DynamicAnimation.OnAnimationEndListener,
        Animator.AnimatorListener {
        override fun onAnimationEnd(
            animation: DynamicAnimation<*>?,
            canceled: Boolean,
            value: Float,
            velocity: Float
        ) {
        }

        override fun onAnimationStart(p0: Animator?) {
            view?.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(p0: Animator?) {

        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationRepeat(p0: Animator?) {
        }

    })

    animator.start()
}

fun alphaHide(
    view: View?,
    duration: Long = DEFAULT_ANIM_DURATION,
    callback: (() -> Unit)? = null
) {

    val propertyXLeft: PropertyValuesHolder =
        PropertyValuesHolder.ofFloat(PROPERTY_ALPHA, 1f, 0f)

    var animator = ValueAnimator()
    animator.setValues(propertyXLeft)
    animator.setDuration(duration)
    animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
        var alpha = animation.getAnimatedValue(PROPERTY_ALPHA) as Float
        view?.alpha = alpha
    })


    animator.addListener(object : DynamicAnimation.OnAnimationEndListener,
        Animator.AnimatorListener {
        override fun onAnimationEnd(
            animation: DynamicAnimation<*>?,
            canceled: Boolean,
            value: Float,
            velocity: Float
        ) {
        }

        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            //view?.visibility = View.GONE
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationRepeat(p0: Animator?) {
        }

    })
    animator.start()
}
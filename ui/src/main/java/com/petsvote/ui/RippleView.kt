package com.petsvote.ui

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.dynamicanimation.animation.DynamicAnimation

class RippleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var xTouch = 0f;
    private var yTouth = 0f;
    private var canvas: Canvas? = null

    var widthView: Int = 0
    var heightView: Int = 0

    private var animator: ValueAnimator? = null
    private var rippleRadius = 0f;
    var isAmim = false

    var pRipple =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.bg_item_color)
            style = Paint.Style.FILL
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
        if(isAmim) drawRipple()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                xTouch = event.x
                yTouth = event.y
                //isAmim = true
                //animRipple()
            }
        }
        return true
    }

    fun startAnim(x: Float, y: Float, width: Int){
        widthView = width
        xTouch = x
        yTouth = y

        isAmim = true
        animRipple()
    }

    private fun drawRipple(){
        canvas!!.drawCircle(xTouch, yTouth, rippleRadius, pRipple);
        if(rippleRadius >= widthView){
            isAmim = false
            invalidate()
        }
    }

    fun animRipple(){
        val propertyXLeft: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat("PROPERTY_RADIUS", 0f, widthView.toFloat())

        animator = ValueAnimator()
        animator!!.setValues(propertyXLeft)
        animator!!.setDuration(200)
        animator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            rippleRadius = animation.getAnimatedValue("PROPERTY_RADIUS") as Float
            invalidate()
        })
        animator!!.addListener(object : DynamicAnimation.OnAnimationEndListener,
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
                //mOnClickListener?.onClick(this@DotIndicator)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
        animator!!.start()
    }

}
package com.petsvote.ui

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.dynamicanimation.animation.DynamicAnimation
import com.petsvote.ui.parallax.DotIndicator
import java.util.*
import kotlin.concurrent.schedule

open class RippleConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var rippleView: RippleView? = null
    var mOnClickListener: RippleOnClickListener? = null
    private var isClick = false

    init {
//        context.withStyledAttributes(attrs, R.styleable.BesieLayout){
//
//            dotColor = getColor(R.styleable.BesieLayout_bl_background,
//                ContextCompat.getColor(context, android.R.color.transparent))
//        }





        viewTreeObserver.addOnGlobalFocusChangeListener(object: ViewTreeObserver.OnGlobalLayoutListener,
            ViewTreeObserver.OnGlobalFocusChangeListener {
            override fun onGlobalLayout() {
                Log.d("BESIE LAYOUT", "width = $width height = $height")
                removeAllViews()

            }

            override fun onGlobalFocusChanged(p0: View?, p1: View?) {
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if(rippleView == null){
                    rippleView = RippleView(context)
                    rippleView?.layoutParams = LayoutParams(width, height)
                    rippleView?.widthView = width
                    addView(rippleView)
                }
                rippleView?.startAnim(event.x, event.y, width)
                Timer().schedule(200) {
                        mOnClickListener?.onClick()
                        isClick = true
                    }
//                if(!isClick && mOnClickListener != null){
//
//                    Timer().schedule(200) {
//                        mOnClickListener?.onClick()
//                        isClick = true
//                    }
//                }

            }
        }
        return super.onTouchEvent(event)
    }

    interface RippleOnClickListener{
        fun onClick()
    }

}
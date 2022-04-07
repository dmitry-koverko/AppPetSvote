package com.petsvote.ui.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.petsvote.ui.R

class BesieLayout : FrameLayout {


    private var defaultColor: Int = Color.TRANSPARENT

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        var inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.besie_layout, this@BesieLayout, true)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.BesieView, defStyle, 0
        )


        findViewById<BesieView>(R.id.root).apply {
            rippleColor = a.getColor(
                R.styleable.BesieView_bv_ripple_color,
                defaultColor
            )
            bgColor = a.getColor(
                R.styleable.BesieView_bv_background_color,
                defaultColor
            )
        }

    }

}
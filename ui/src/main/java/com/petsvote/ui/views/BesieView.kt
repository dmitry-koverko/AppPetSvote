package com.petsvote.ui.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.petsvote.ui.R

class BesieView : View {

    private val BESIE_RADIUS = 128
    private val DURATION_ANIMATION: Long = 200

    private var _backgroundColor: Int = Color.TRANSPARENT
    private var _rippleColor: Int = Color.TRANSPARENT
    private var landmark = 0f

    private var xTouch = 0f;
    private var yTouth = 0f;
    private var isAmim = false
    private var rippleRadius = 0f
    private var animator: ValueAnimator? = null


    var mOnClickListener: OnClickListener? = null

    var bgColor: Int = _backgroundColor
        set(value) {
            field = value
            backgroundPaint.color = value
        }

    var rippleColor: Int = _rippleColor
        set(value) {
            field = value
            ripplePaint.color = value
        }

    var backgroundPaint =
        Paint().apply {
            isAntiAlias = true
            color = bgColor
            style = Paint.Style.FILL
        }
        set(value) {
            field = value
            invalidate()
        }

    var ripplePaint =
        Paint().apply {
            isAntiAlias = true
            color = rippleColor
            style = Paint.Style.FILL
        }
        set(value) {
            field = value
            invalidate()
        }

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
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.BesieView, defStyle, 0
        )

        rippleColor = a.getColor(
            R.styleable.BesieView_bv_ripple_color,
            _rippleColor
        )

        bgColor = a.getColor(
            R.styleable.BesieView_bv_background_color,
            _backgroundColor
        )

        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        this.landmark = width.coerceAtMost(height) / 2f

        drawBesie(canvas)
        if (isAmim) drawRipple(canvas)
    }

    private fun drawBesie(canvas: Canvas) {
        var path = Path()
        path.moveTo(this.landmark, 0f)
        path.quadTo(
            this.landmark / BESIE_RADIUS, this.landmark / BESIE_RADIUS, 0f,
            this.landmark
        )
        path.lineTo(0f, height - this.landmark)
        path.quadTo(
            (this.landmark / BESIE_RADIUS).toFloat(),
            height - (this.landmark / BESIE_RADIUS).toFloat(),
            this.landmark,
            height.toFloat()
        )
        path.lineTo(width - this.landmark, height.toFloat());
        path.quadTo(
            width - this.landmark / BESIE_RADIUS, height - this.landmark / BESIE_RADIUS,
            width.toFloat(), height - this.landmark
        )
        path.lineTo(width.toFloat(), this.landmark)
        path.quadTo(
            width - this.landmark / BESIE_RADIUS, this.landmark / BESIE_RADIUS,
            width - this.landmark, 0f
        );
        path.lineTo(this.landmark, 0f)

        canvas.clipPath(path)
        canvas.drawPath(path, backgroundPaint);
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        xTouch = event.x
        yTouth = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mOnClickListener?.onClick(this)
                isAmim = true
                startRipple()
            }
        }
        return true
    }

    private fun startRipple() {
        animator = ValueAnimator.ofFloat(0f, width.toFloat()).apply {
            duration = DURATION_ANIMATION
            interpolator = LinearInterpolator()
            addUpdateListener { valueAnimator ->
                rippleRadius = valueAnimator.animatedValue as Float
                invalidate()
            }
        }
        animator?.start()
    }

    private fun drawRipple(canvas: Canvas) {
        canvas.drawCircle(xTouch, yTouth, rippleRadius, ripplePaint);
        if (rippleRadius >= width) {
            isAmim = false
            invalidate()
        }
    }

}
package com.petsvote.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat

open class RoundedImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private var radius = context.resources.displayMetrics.density * 16
    var paint =
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.ui_gray)
            style = Paint.Style.FILL
        }
    private var path = Path()

    override fun onDraw(canvas: Canvas) {
        path.reset()
        path.addRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, Path.Direction.CCW)
        canvas.clipPath(path)
        canvas.drawPath(path, paint)
        super.onDraw(canvas)
    }
}
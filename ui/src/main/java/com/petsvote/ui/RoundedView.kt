package com.petsvote.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class RoundedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint().apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.bg_layers_1)
        style = Paint.Style.FILL
    }

    private var path = Path()
    private var v16 = context.resources.displayMetrics.density * 32
    private val BESIEDOT = 128

    override fun onDraw(canvas: Canvas) {
        path.reset()
        path.moveTo(v16, 0f)
        path.quadTo(v16 /BESIEDOT, v16 /BESIEDOT, 0f, v16)
        path.lineTo(0f, 0f)
        path.moveTo(v16, 0f)


        var path2 = Path()
        path2.moveTo(0f, (height - v16).toFloat())
        path2.quadTo(v16 /BESIEDOT, height -v16 /BESIEDOT, v16, height.toFloat())
        path2.lineTo(0f, height.toFloat())
        path2.moveTo(0f, (height - 16).toFloat())

        var path3 = Path()
        path3.moveTo(width - v16, height.toFloat())
        path3.quadTo(width - v16 /BESIEDOT, height -v16 /BESIEDOT, width.toFloat(), height.toFloat() - v16)
        path3.lineTo(width.toFloat(), height.toFloat())
        path3.moveTo(width - v16, height.toFloat())

        var path4 = Path()
        path4.moveTo(width - v16, 0f)
        path4.quadTo(width - v16 /BESIEDOT, v16 /BESIEDOT, width.toFloat(), v16)
        path4.lineTo(width.toFloat(), 0f)
        path4.moveTo(width - v16, 0f)


        //canvas.clipPath(path)
        //canvas.clipPath(path2)
        canvas.drawPath(path, paint);
        canvas.drawPath(path2, paint);
        canvas.drawPath(path3, paint);
        canvas.drawPath(path4, paint);
        path.reset()
    }
}
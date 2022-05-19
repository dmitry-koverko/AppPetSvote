package com.petsvote.ui.parallax

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import com.petsvote.ui.R
import kotlin.math.abs
import kotlin.math.roundToInt

class ParallaxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val TAG = ParallaxView::class.java.name
    private val pageIndicator: PageIndicator

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    var list = listOf<String>()
        set(value) {
            field = value
            viewPagerAdapter.update(value)
            pageIndicator.setCountIndicators(value.size)
        }

    init{
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.parallax_view, this, true)

        viewPagerAdapter = ViewPagerAdapter(context, list)
        pageIndicator =  findViewById<PageIndicator>(R.id.page_indicators)
        pageIndicator.apply {
            setCountIndicators(viewPagerAdapter.itemCount)
        }

        findViewById<ViewPager2>(R.id.view_pager).apply {
            orientation = ORIENTATION_VERTICAL
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    Log.d(TAG, "OnPageChangeCallback " +
                            "positionOffset = ${positionOffset * 100} position = $position")
                    pageIndicator.apply {
                        currentPoint = position
                        if(positionOffset != 0.0f )
                            setOffsetTo((positionOffset * 100).toInt())
                    }
                }
            })
            setPageTransformer { page, position ->

            }
        }

    }

    private var radius = context.resources.displayMetrics.density * 16
    private var paint =
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
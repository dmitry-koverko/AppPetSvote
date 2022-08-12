package com.petsvote.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.marginLeft

class ItemTopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var tab: FrameLayout
    private var tabIcon: ImageView
    private var tabIconSelect: ImageView
    private var view: View

    init {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_topbar, this, true)

        tab = findViewById<FrameLayout>(R.id.fr)
        tabIcon = findViewById(R.id.fr_icon)
        tabIconSelect = findViewById(R.id.fr_icon_selected)
        view = findViewById(R.id.leftView)
    }

    fun selected(select: Float, unselect: Float){
        //Log.d("SELECT = ", "select = $select | unselect = $unselect")
        tabIcon.alpha = select
        tabIconSelect.alpha = unselect / 100

    }

    fun setLpIcon(widthIcon: Float, heightIcon: Float){

        //Log.d("ITEM TOP BAR", "height = $heightIcon, width = $widthIcon")

        var lpImage = tabIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = widthIcon.toInt()
            height = heightIcon.toInt()
        }

        tabIcon.layoutParams = lpImage
        tabIconSelect.layoutParams = lpImage
    }

    fun setLp(widthD: Int, heightD: Int){
        Log.d("ITEM TOP BAR", "height = $height, width = $width")
        var lp = view.layoutParams  as FrameLayout.LayoutParams
        lp.apply {
            width = widthD
            height = heightD
        }
        view.layoutParams = lp
    }

    fun getlpTabWidth() = view.layoutParams.width
}
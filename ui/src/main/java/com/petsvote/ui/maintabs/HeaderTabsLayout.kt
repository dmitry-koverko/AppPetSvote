package com.petsvote.ui.maintabs

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.petsvote.ui.R

class HeaderTabsLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    private var tabRating: TabLayout.Tab
    private var tabVote: TabLayout.Tab
    private var tabProfile: TabLayout.Tab

    private var screenWidth = resources.displayMetrics.widthPixels
    private var padding = screenWidth / 2
    private var tabDefaultWith = resources.displayMetrics.density * 24
    private var defaultPadding = resources.displayMetrics.density * 24
    private var defaultMargin = resources.displayMetrics.density * 24
    private var tabDefaultIconHeight = resources.displayMetrics.density * 24
    private var tabDefaultCenterWidth = (screenWidth - tabDefaultWith * 2).toInt()

    init {

        Log.d("HEADERS_TABS", "screenSize = $screenWidth")
        Log.d("HEADERS_TABS", "padding = $padding")
        Log.d("HEADERS_TABS", "tabDefaultWith = $tabDefaultWith")
        Log.d("HEADERS_TABS", "tabDefaultCenterWidth = $tabDefaultCenterWidth")

        tabRating = this.newTab()
        tabRating.setCustomView(R.layout.item_tab)
        tabRating.customView?.findViewById<ImageView>(R.id.ic_icon)
            ?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_rating))
        var lpRating = LinearLayout.LayoutParams(tabDefaultWith.toInt(), tabDefaultIconHeight.toInt())
        lpRating.leftMargin = (padding / resources.displayMetrics.density).toInt() - 36
        tabRating.customView?.layoutParams = lpRating


        tabVote = this.newTab()
        tabVote.setCustomView(R.layout.item_tab)
        tabVote.customView?.findViewById<ImageView>(R.id.ic_icon)
            ?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_star))
        tabVote.customView?.layoutParams =
            LinearLayout.LayoutParams(tabDefaultCenterWidth.toInt(), tabDefaultIconHeight.toInt())

        tabProfile = this.newTab()
        tabProfile.setCustomView(R.layout.item_tab)
        tabProfile.customView?.findViewById<ImageView>(R.id.ic_icon)
            ?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_profile))
        tabProfile.customView?.layoutParams =
            LinearLayout.LayoutParams(tabDefaultWith.toInt(), tabDefaultIconHeight.toInt())

        this.addTab(tabRating)
        this.addTab(tabVote, true)
        this.addTab(tabProfile)

        initDefaultTabs()
    }


    private fun initDefaultTabs() {

        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<ImageView>(R.id.ic_icon)?.getDrawable()
                    ?.let {
                        DrawableCompat.setTint(
                            it,
                            ContextCompat.getColor(context, R.color.ui_primary)
                        )
                    };

                if (selectedTabPosition == 0) {
                    var lp = LinearLayout.LayoutParams(
                        tabDefaultCenterWidth.toInt(),
                        tabDefaultIconHeight.toInt()
                    )
                    //lp.leftMargin =
                    tab.customView?.layoutParams = lp

                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<ImageView>(R.id.ic_icon)?.getDrawable()
                    ?.let {
                        DrawableCompat.setTint(
                            it,
                            ContextCompat.getColor(context, R.color.ui_gray)
                        )
                    };
            }
        })


    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d("HEADERS_TABS", "onLayout: l = $l ||| r =$r")
        Log.d("HEADERS_TABS", "onLayout: ${tabRating.view.width}.")
        super.onLayout(changed, l, t, r, b)
    }

}
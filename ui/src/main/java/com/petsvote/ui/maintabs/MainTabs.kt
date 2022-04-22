package com.petsvote.ui.maintabs

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.tabs.TabLayout
import com.petsvote.ui.R

class MainTabs @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var tabs: TabLayout?
    private var tabRating: TabLayout.Tab?
    private var tabVote: TabLayout.Tab?
    private var tabProfile: TabLayout.Tab?

    private var screenWidth = resources.displayMetrics.widthPixels
    private var tabDefaultHeight = (resources.displayMetrics.density * 56).toInt()
    private var tabDefaultWidth = (resources.displayMetrics.density * 24).toInt()
    private var tabDefaultDiff = (resources.displayMetrics.density * 16).toInt()
    private var tabDefaultSelectedWidth = (resources.displayMetrics.density * 32).toInt()
    private var tabDefaultCenterWidth =
        (screenWidth / 2) + (resources.displayMetrics.density * 48).toInt()
    private var padding = (screenWidth / 2) + (resources.displayMetrics.density * 14).toInt()
    private var tabUnselectedCenterWidth =
        (screenWidth) + (resources.displayMetrics.density * 104).toInt()
    private var marginCenter = (padding / 2) - resources.displayMetrics.density.toInt() * 112

    var states = arrayOf(
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled)
    )

    var colors = intArrayOf(
        ContextCompat.getColor(context, R.color.selected_tab_icon),
        ContextCompat.getColor(context, R.color.unselected_tab_icon),
        ContextCompat.getColor(context, R.color.unselected_tab_icon)
    )

    private var animator: ValueAnimator? = null
    private val ANIMATE_DURATION = 300L
    private val ANIMATE_DURATION_MIN = 100L
    private val selectedColor = ContextCompat.getColor(context, R.color.selected_tab_icon)
    private val unselectedColor = ContextCompat.getColor(context, R.color.unselected_tab_icon)

    init {

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.maintabs, this, true)

        tabs = findViewById<TabLayout>(R.id.tabs)

        tabRating = tabs?.getTabAt(0)
        tabVote = tabs?.getTabAt(1)
        tabProfile = tabs?.getTabAt(2)

        tabs?.tabIconTint = ColorStateList(states, colors)

        tabVote?.customView?.findViewById<FrameLayout>(R.id.ic_icon)?.layoutParams =
            LinearLayout.LayoutParams(tabDefaultCenterWidth, tabDefaultHeight)

        initListener()
        tabs?.selectTab(tabProfile)
        tabs?.selectTab(tabVote)

    }

    private fun initListener() {

        tabs?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tabs?.selectedTabPosition) {
                    0 -> {
                        startAnimToLeft()
                    }
                    1 -> selectedCenterTab()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tabs?.selectedTabPosition) {
                    0 -> {
                        startAnimUnselectedCenter()
                    }
                    1 -> startAnimFromLeft()
                }
            }
        })

    }


    private fun unselectedCenterTabToLeft(position: Int) {
        val tabView = tabVote?.customView?.findViewById<FrameLayout>(R.id.ic_icon)
        var lp = tabView?.layoutParams as LinearLayout.LayoutParams
        lp.marginStart = marginCenter * position / 100
        var defaultUnselectedCenterWith = (screenWidth / 2) + (resources.displayMetrics.density.toInt() * 56) * 2
        lp.width =
            (tabDefaultCenterWidth - (tabDefaultCenterWidth * position / 100)) + tabDefaultSelectedWidth
        tabView.layoutParams = lp

    }

    private fun animUnselectedVoteIcon(position: Int) {

        var image = tabVote?.customView?.findViewById<ImageView>(R.id.icon)
        var lpImage = image?.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL
            width = calculateUnSelectedSizeIcon(position)
            height = calculateUnSelectedSizeIcon(position)
        }
        image.layoutParams = lpImage

        ContextCompat.getDrawable(context, R.drawable.ic_icon_vote)?.apply {
            DrawableCompat.wrap(this).apply {
                setTint(
                    ColorUtils.blendARGB(
                        selectedColor,
                        unselectedColor, calculateAlphaSelected(position)
                    )
                )
                setTintMode(PorterDuff.Mode.SRC_IN)
                image.setImageDrawable(this)
            }
        }
    }

    private fun selectedCenterTab() {

        val tabView = tabVote?.customView?.findViewById<FrameLayout>(R.id.ic_icon)
        var lp = tabView?.layoutParams as LinearLayout.LayoutParams
        lp.apply {
            width = tabDefaultCenterWidth
        }
        tabView.layoutParams = lp

    }

    private fun unselectedTabLeft() {
        val tabViewImage = tabRating?.customView?.findViewById<ImageView>(R.id.icon)
        var lpImage = tabViewImage?.layoutParams as FrameLayout.LayoutParams
        lpImage.gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL
        lpImage.rightMargin = 2
        tabViewImage.layoutParams = lpImage
        tabViewImage.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_icon_rating
            )
        )

        val tabView = tabRating?.customView?.findViewById<FrameLayout>(R.id.ic_icon)
        var lp = tabView?.layoutParams as LinearLayout.LayoutParams
        lp.width = tabDefaultWidth
        tabView.layoutParams = lp

    }

    private fun startAnimUnselectedCenter() {
        val propertyXLeft: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 100)
        val animator = ValueAnimator()
        animator!!.setValues(propertyXLeft)
        animator!!.setDuration(ANIMATE_DURATION)
        animator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            unselectedCenterTabToLeft(position)
            animUnselectedVoteIcon(position)
        })
        animator!!.start()
    }

    @SuppressLint("Recycle")
    private fun startAnimToLeft() {
        val propertyXLeft: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 100)
        val animator = ValueAnimator()
        animator.setValues(propertyXLeft)
        animator.setDuration(ANIMATE_DURATION)
        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            selectTabLeft(position)
            animSelectedRatingIcon(position)
        })
        animator.start()
    }

    @SuppressLint("Recycle")
    private fun startAnimFromLeft() {
        val propertyXLeft: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 100)
        val animatorIcon = ValueAnimator()
        animatorIcon.setValues(propertyXLeft)
        animatorIcon.setDuration(ANIMATE_DURATION)
        animatorIcon.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animUnselectedRatingIcon(position)
        })
        animatorIcon.start()

        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 100)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION_MIN)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            unselectTabLeft(position)
        })
        animatorLP.start()
    }

    private fun selectTabLeft(position: Int) {

        val tabView = tabRating?.customView?.findViewById<FrameLayout>(R.id.ic_icon)
        var lp = LinearLayout.LayoutParams(0, tabDefaultHeight)
        lp.width = tabDefaultWidth + ((padding - tabDefaultWidth) * position / 100)
        tabView?.layoutParams = lp

    }

    private fun unselectTabLeft(position: Int) {

        val tabView = tabRating?.customView?.findViewById<FrameLayout>(R.id.ic_icon)
        var lp = LinearLayout.LayoutParams(0, tabDefaultHeight)
        lp.width = tabDefaultWidth + (padding - (padding * position / 100))
        tabView?.layoutParams = lp

    }

    private fun animSelectedRatingIcon(position: Int) {

        var image = tabRating?.customView?.findViewById<ImageView>(R.id.icon)

        var lpImage = image?.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
            rightMargin = 2
            width = calculateSelectedSizeIcon(position)
            height = calculateSelectedSizeIcon(position)
        }
        image.layoutParams = lpImage

        ContextCompat.getDrawable(context, R.drawable.ic_rating_selected)?.apply {
            DrawableCompat.wrap(this).apply {
                setTint(
                    ColorUtils.blendARGB(
                        unselectedColor,
                        selectedColor, calculateAlphaSelected(position)
                    )
                )
                setTintMode(PorterDuff.Mode.SRC_IN)
                image.setImageDrawable(this)
            }
        }

    }

    private fun animUnselectedRatingIcon(position: Int) {

        var image = tabRating?.customView?.findViewById<ImageView>(R.id.icon)

        var lpImage = image?.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL
            rightMargin = 2
            width = calculateUnSelectedSizeIcon(position)
            height = calculateUnSelectedSizeIcon(position)
        }
        image.layoutParams = lpImage

        ContextCompat.getDrawable(context, R.drawable.ic_icon_rating)?.apply {
            DrawableCompat.wrap(this).apply {
                setTint(
                    ColorUtils.blendARGB(
                        selectedColor,
                        unselectedColor, calculateAlphaSelected(position)
                    )
                )
                setTintMode(PorterDuff.Mode.SRC_IN)
                image.setImageDrawable(this)
            }
        }
    }

    private fun calculateAlphaSelected(position: Int): Float {
        return position * 0.01f
    }

    private fun calculateAlphaUnSelected(position: Int): Float {
        return 255 - position * 2.55f
    }

    private fun calculateSelectedSizeIcon(position: Int): Int {
        var diff = (tabDefaultSelectedWidth - tabDefaultWidth) * position / 100
        return tabDefaultWidth + diff
    }

    private fun calculateUnSelectedSizeIcon(position: Int): Int {
        var diff = (tabDefaultSelectedWidth - tabDefaultWidth) * position / 100
        return tabDefaultSelectedWidth - diff
    }
}
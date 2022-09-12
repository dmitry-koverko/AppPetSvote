package com.petsvote.ui.maintabs

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import com.petsvote.ui.R

@SuppressLint("ClickableViewAccessibility")
class TopTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var tabDefaultHeight = (resources.displayMetrics.density * 32).toInt()
    private var tabDefaultWidth = (resources.displayMetrics.density * 24).toInt()
    private var tabSelectedWidth = (resources.displayMetrics.density * 32).toInt()
    private var tabDefaultPadding = (resources.displayMetrics.density * 16).toInt()
    private var screenWidth = resources.displayMetrics.widthPixels
    private var defaultDiff = tabDefaultWidth * 2 - tabDefaultPadding * 2

    private var tabRating: FrameLayout
    private var tabRatingIcon: ImageView
    private var tabRatingIconSelect: ImageView

    private var tabVote: FrameLayout
    private var tabVoteIcon: ImageView
    private var tabVoteIconSelect: ImageView

    private var tabProfile: FrameLayout
    private var tabProfileIcon: ImageView
    private var tabProfileIconSelect: ImageView

    private var scroll: HorizontalScrollView

    var mTopTabLayoutListener: TopTabLayoutListener? = null

    private val ANIMATE_DURATION = 100L

    private var currentTab = 1

    init {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.top_bar_layout, this, true)

        tabRating = findViewById<FrameLayout>(R.id.fr_rating)
        tabRatingIcon = findViewById(R.id.fr_rating_icon)
        tabRatingIconSelect = findViewById(R.id.fr_rating_icon_selected)

        tabVote = findViewById<FrameLayout>(R.id.fr_vote)
        tabVoteIcon = findViewById(R.id.fr_vote_icon)
        tabVoteIconSelect = findViewById(R.id.fr_vote_icon_selected)

        tabProfile = findViewById<FrameLayout>(R.id.fr_profile)
        tabProfileIcon = findViewById(R.id.fr_profile_icon)
        tabProfileIconSelect = findViewById(R.id.fr_profile_icon_selected)

        scroll = findViewById(R.id.scroll)

        initDefaultsParams()
        defaultScroll()

        scroll.setOnTouchListener { p0, p1 -> true }

        tabRating.setOnClickListener {
            mTopTabLayoutListener?.selectTabPosition(0)
//            animateSelectRating()
//            animateUnSelectVote()
//            scrollToLeft()
//            currentTab = 0
        }

        tabVote.setOnClickListener {
            mTopTabLayoutListener?.selectTabPosition(1)
//            if (currentTab == 0) {
//                animateUnSelectRating()
//                animateSelectVote()
//                defaultScroll()
//            } else if (currentTab == 2) {
//                animateUnSelectProfile()
//                animateSelectVote()
//                defaultScroll()
//            }
//            currentTab = 1
        }

        tabProfile.setOnClickListener {
//            animateSelectProfile()
//            animateUnSelectVote()
//            scrollToRight()
//            currentTab = 2
            mTopTabLayoutListener?.selectTabPosition(2)
        }
    }

    private fun initDefaultsParams() {

        tabRating.layoutParams =
            LinearLayoutCompat.LayoutParams(screenWidth / 2 + tabDefaultWidth / 2, tabDefaultHeight)

        tabVote.layoutParams =
            LinearLayoutCompat.LayoutParams(
                screenWidth - tabDefaultWidth * 2 - tabDefaultPadding * 2,
                tabDefaultHeight
            )

        tabProfile.layoutParams =
            LinearLayoutCompat.LayoutParams(screenWidth / 2 + tabDefaultWidth / 2, tabDefaultHeight)

    }

    private fun defaultScroll() {

        var t1 = tabRating.layoutParams.width
        var t2 = tabDefaultPadding + tabDefaultWidth

        scroll.postDelayed(
            Runnable { scroll.smoothScrollTo(t1 - t2, 0) },
            20
        )
    }

    private fun scrollToLeft() {
        scroll.postDelayed(
            Runnable { scroll.smoothScrollTo(0, 0) },
            10
        )
    }

    private fun scrollToRight() {
        scroll.postDelayed(
            Runnable { scroll.smoothScrollTo(2000, 0) },
            10
        )
    }

    private fun animateSelectProfile() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateSelectProfileByPosition(position)
        })
        animatorLP.start()
    }

    private fun animateUnSelectProfile() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateUnSelectProfileByPosition(position)
        })
        animatorLP.start()
    }

    fun animateSelectProfileByPosition(position: Int, isViewPager: Boolean = false) {

        var lpImage = tabProfileIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateSelectedSizeIcon(position)
            height = calculateSelectedSizeIcon(position)
        }
        tabProfileIcon.layoutParams = lpImage
        tabProfileIconSelect.layoutParams = lpImage

        tabProfileIconSelect.alpha = if(!isViewPager) calculateAlphaSelected(position)
        else calculateAlphaSelected(position * 2)

        tabProfileIcon.alpha = (if(!isViewPager) calculateAlphaUnSelected(position)
        else calculateAlphaUnSelected(position * 2)) / 100

    }

    fun animateUnSelectProfileByPosition(position: Int) {

        var lpImage = tabProfileIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateUnSelectedSizeIcon(position)
            height = calculateUnSelectedSizeIcon(position)
        }
        tabProfileIcon.layoutParams = lpImage
        tabProfileIconSelect.layoutParams = lpImage

        tabProfileIcon.alpha = calculateAlphaSelected(position)
        tabProfileIconSelect.alpha = calculateAlphaUnSelected(position) / 100

    }

    private fun animateSelectVote() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateSelectVoteByPosition(position)
        })
        animatorLP.start()
    }

    private fun animateUnSelectVote() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateUnSelectVoteByPosition(position)
        })
        animatorLP.start()
    }

    fun animateSelectVoteByPosition(position: Int, isViewPager: Boolean = false) {

        var lpImage = tabVoteIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateSelectedSizeIcon(position)
            height = calculateSelectedSizeIcon(position)
        }
        tabVoteIcon.layoutParams = lpImage
        tabVoteIconSelect.layoutParams = lpImage

        tabVoteIconSelect.alpha = if(!isViewPager) calculateAlphaSelected(position)
        else calculateAlphaSelected(position)

        tabVoteIcon.alpha = if(!isViewPager) calculateAlphaUnSelected(position)
        else calculateAlphaUnSelected(position)/100

    }

    fun animateUnSelectVoteByPosition(position: Int, isViewPager: Boolean = false) {

        var lpImage = tabVoteIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateUnSelectedSizeIcon(position)
            height = calculateUnSelectedSizeIcon(position)
        }
        tabVoteIcon.layoutParams = lpImage
        tabVoteIconSelect.layoutParams = lpImage


        tabVoteIcon.alpha = if(!isViewPager) calculateAlphaSelected(position)
        else calculateAlphaSelected(position)
//
        tabVoteIconSelect.alpha = if(!isViewPager) calculateAlphaUnSelected(position)
        else calculateAlphaUnSelected(position) / 100

    }

    private fun animateSelectRating() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateSelectRatingByPosition(position)
        })
        animatorLP.start()
    }

    private fun animateUnSelectRating() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("padding", 0, 1000)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(ANIMATE_DURATION)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var position = animation.getAnimatedValue("padding") as Int
            animateUnSelectRatingByPosition(position)
        })
        animatorLP.start()
    }

    fun animateSelectRatingByPosition(position: Int) {

        var lpImage = tabRatingIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateSelectedSizeIcon(position)
            height = calculateSelectedSizeIcon(position)
        }
        tabRatingIcon.layoutParams = lpImage
        tabRatingIconSelect.layoutParams = lpImage

        tabRatingIconSelect.alpha = calculateAlphaSelected(position)
        tabRatingIcon.alpha = calculateAlphaUnSelected(position)

    }

    fun animateUnSelectRatingByPosition(position: Int, isViewPager: Boolean = false) {

        var lpImage = tabRatingIcon.layoutParams as FrameLayout.LayoutParams

        lpImage.apply {
            width = calculateUnSelectedSizeIcon(position)
            height = calculateUnSelectedSizeIcon(position)
        }
        tabRatingIcon.layoutParams = lpImage
        tabRatingIconSelect.layoutParams = lpImage

        tabRatingIcon.alpha = if (!isViewPager) calculateAlphaSelected(position)
        else calculateAlphaSelected(position * 2)

        tabRatingIconSelect.alpha = (if (!isViewPager) calculateAlphaUnSelected(position )
        else calculateAlphaUnSelected(position * 2)) / 100
    }


    private fun calculateSelectedSizeIcon(position: Int): Int {
        var diff = (tabSelectedWidth - tabDefaultWidth) * position / 100
        return tabDefaultWidth + diff
    }

    private fun calculateUnSelectedSizeIcon(position: Int): Int {
        var diff = (tabSelectedWidth - tabDefaultWidth) * position / 100
        return tabSelectedWidth - diff
    }

    private fun calculateAlphaSelected(position: Int): Float {
        return position * 0.01f
    }

    private fun calculateAlphaUnSelected(position: Int): Float {
        return 100 - position.toFloat()
    }

    fun scrollRatingToCenter(positionOffset: Float) {
        var position = positionOffset * 100
        var t1 = tabRating.layoutParams.width
        var t2 = tabDefaultPadding + tabDefaultWidth
        var diff = t1 - t2
        var scrollX = diff * position / 100
        scroll.postDelayed(
            Runnable { scroll.smoothScrollTo(scrollX.toInt(), 0) },
            10
        )
        animateUnSelectRatingByPosition(position.toInt(), true)
        animateSelectVoteByPosition(position.toInt(), true)
    }

    fun scrollVoteToLeft(positionOffset: Float){

        var position = positionOffset * 100
        var t1 = tabRating.layoutParams.width - (tabDefaultPadding + tabDefaultWidth)
        var t2 = tabVote.layoutParams.width
        var diff = t2 * position / 100

        var scrollX = t1 + diff

        scroll.postDelayed(
            Runnable { scroll.smoothScrollTo(scrollX.toInt(), 0) },
            10
        )
        animateUnSelectVoteByPosition(position.toInt(), true)
        animateSelectProfileByPosition(position.toInt(), true)
    }

    interface TopTabLayoutListener {
        fun selectTabPosition(position: Int)
    }
}
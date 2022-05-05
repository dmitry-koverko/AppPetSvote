package com.petsvote.ui.bottomstar

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.petsvote.ui.BesieLayout
import com.petsvote.ui.R

class BottomStars @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var starActiveOne: ImageView
    private var starActiveTwo: ImageView
    private var starActiveThree: ImageView
    private var starActiveFour: ImageView
    private var starActiveFive: ImageView

    private var starOne: BesieLayout
    private var starTwo: BesieLayout
    private var starThree: BesieLayout
    private var starFour: BesieLayout
    private var starFive: BesieLayout

    private var listStars = mutableListOf<ImageView>()
    private var isAnim = false

    var mBottomStarsListener: BottomStarsListener? = null

    init {
        View.inflate(context, R.layout.bottom_stars, this)

        starActiveOne = findViewById(R.id.starActiveOne)
        starActiveTwo = findViewById(R.id.starActiveTwo)
        starActiveThree = findViewById(R.id.starActiveThree)
        starActiveFour = findViewById(R.id.starActiveFour)
        starActiveFive = findViewById(R.id.starActiveFive)

        starOne = findViewById(R.id.star1)
        starTwo = findViewById(R.id.star2)
        starThree = findViewById(R.id.star3)
        starFour = findViewById(R.id.star4)
        starFive = findViewById(R.id.star5)

        listStars.add(starActiveOne)
        listStars.add(starActiveTwo)
        listStars.add(starActiveThree)
        listStars.add(starActiveFour)
        listStars.add(starActiveFive)

        starOne.setOnClickListener {
            if (isAnim) return@setOnClickListener
            startVoteAnim(0)
            mBottomStarsListener?.vote(1)
        }
        starTwo.setOnClickListener {
            if (isAnim) return@setOnClickListener
            startVoteAnim(1)
            mBottomStarsListener?.vote(2)
        }
        starThree.setOnClickListener {
            if (isAnim) return@setOnClickListener
            startVoteAnim(2)
            mBottomStarsListener?.vote(3)
        }
        starFour.setOnClickListener {
            if (isAnim) return@setOnClickListener
            startVoteAnim(3)
            mBottomStarsListener?.vote(4)
        }
        starFive.setOnClickListener {
            if (isAnim) return@setOnClickListener
            startVoteAnim(4)
            mBottomStarsListener?.vote(5)
        }
    }

    fun startVoteAnim(position: Int) {
        isAnim = true
        var isUnselected = false
        for (i in 0..position)
            listStars[i].startAnimActive()

        object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                if(!isUnselected){
                    for (i in 0..position)
                        listStars[i].startAnimUnActive()
                    isUnselected = true
                }
            }

            override fun onFinish() {
                isAnim = false
            }
        }.start()

    }

    private fun ImageView.startAnimActive() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(500)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var alpha = animation.getAnimatedValue("alpha") as Float
            this.alpha = alpha
        })
        animatorLP.start()
    }

    private fun ImageView.startAnimUnActive() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat("alpha", 1f, 0f)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.setDuration(500)
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var alpha = animation.getAnimatedValue("alpha") as Float
            this.alpha = alpha
        })
        animatorLP.start()
    }

    interface BottomStarsListener {
        fun vote(position: Int)
    }
}
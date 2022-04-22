package com.petsvote.app

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import me.vponomarenko.injectionmanager.x.XInjectionManager

class MainActivity : AppCompatActivity() {

    private val ICON_TIME: Long = 1500
    private val TRANSPARENT_ANIMATION: Long = 300L
    private val ANIMATION_ALPHA_NAME = "alpha"

    private val navigator: Navigator by lazy {
        XInjectionManager.findComponent<Navigator>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUIStart()
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    private fun setUIStart() {
        object : CountDownTimer(ICON_TIME, ICON_TIME) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                animateSplash()
            }
        }.start()
    }

    private fun animateSplash() {
        val propertyLP: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat(ANIMATION_ALPHA_NAME, 1f, 0f)
        val animatorLP = ValueAnimator()
        animatorLP.setValues(propertyLP)
        animatorLP.duration = TRANSPARENT_ANIMATION
        animatorLP.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var alpha = animation.getAnimatedValue(ANIMATION_ALPHA_NAME) as Float
            findViewById<FrameLayout>(R.id.frame).alpha = alpha
        })
        animatorLP.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                findViewById<FrameLayout>(R.id.frame).visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })
        animatorLP.start()
    }
}
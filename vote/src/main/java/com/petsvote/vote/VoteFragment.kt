package com.petsvote.vote

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.ui.bottomstar.BottomStars
import com.petsvote.vote.databinding.FragmentVoteBinding

class VoteFragment : BaseFragment(R.layout.fragment_vote) {

    private var binding: FragmentVoteBinding? = null
    private var adapter: VoteAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteBinding.bind(view)
        adapter = activity?.let { VoteAdapter(it) }
        binding?.pager?.adapter = adapter
        binding?.pager?.isUserInputEnabled = false
        binding?.pager?.setCurrentItem(1, false)

        binding?.bottomStars?.mBottomStarsListener = object : BottomStars.BottomStarsListener{
            override fun vote(position: Int) {
                val myFragment =
                    activity?.supportFragmentManager?.findFragmentByTag("f" + binding?.pager?.currentItem)
                myFragment?.tag?.let { it1 -> log(it1) }
                if(myFragment is ItemVoteFragment) {
                    myFragment.setRating(position)
                    myFragment.startAnim({ startDragToLeft() })
                }
            }

        }

    }

    fun startDragToLeft(){
        binding?.pager?.fakeDrag(duration = 300L, numberOfPages = 1)
    }

    override fun initObservers() {

    }

    fun ViewPager2.fakeDrag(leftToRight: Boolean = true, duration: Long, numberOfPages: Int) {
        val pxToDrag: Int = this.width
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            var currentPxToDrag: Float = (currentValue - previousValue).toFloat() * numberOfPages
            when {
                leftToRight -> {
                    currentPxToDrag *= -1
                }
            }
            this.fakeDragBy(currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { this@fakeDrag.beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { this@fakeDrag.endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
        })
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.start()
    }
}
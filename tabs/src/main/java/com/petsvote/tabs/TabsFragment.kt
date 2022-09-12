package com.petsvote.tabs

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.petsvote.domain.flow.findPetToVote
import com.petsvote.rating.RatingEmptyFragment
import com.petsvote.rating.RatingFragment
import com.petsvote.tabs.databinding.FragmentTabsBinding
import com.petsvote.ui.maintabs.TopTabLayout
import com.petsvote.user.simple.SimpleUserFragment
import com.petsvote.vote.VoteFragment
import kotlinx.coroutines.flow.collect

class TabsFragment: Fragment(R.layout.fragment_tabs), ViewPager.OnPageChangeListener,
    TopTabLayout.TopTabLayoutListener {

    private var binding: FragmentTabsBinding? = null
    private val ANIM_DURATION = 250L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTabsBinding.bind(view)
        setUpTabs()
    }

    private fun setUpTabs() {
        binding?.viewPager?.adapter = ViewPagerAdapter(childFragmentManager)
        binding?.viewPager?.addOnPageChangeListener(this)
        binding?.viewPager?.setCurrentItem(1, true)
        binding?.tabs?.mTopTabLayoutListener = this

        lifecycleScope.launchWhenResumed {
            findPetToVote.collect { votePet ->
                votePet?.let {
                    binding?.viewPager?.invalidate()
                    binding?.viewPager?.currentItem = 1
                }
            }
        }
    }//48376037

    class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

        private val COUNT = 3

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> RatingFragment.newInstance()
                1 -> VoteFragment()
                2 -> SimpleUserFragment()
                else -> BlankFragment()
            }
        }

        override fun getCount(): Int {
            return COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Tab " + (position + 1)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(position == 0 && positionOffset != 0f) binding?.tabs?.scrollRatingToCenter(positionOffset)
        else if(position == 1 && positionOffset != 0f) binding?.tabs?.scrollVoteToLeft(positionOffset)
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun selectTabPosition(position: Int) {
        //binding?.viewPager?.setCurrentItem(position, true)
        if(binding?.viewPager?.currentItem == 0 && position == 1)
            binding?.viewPager?.fakeDrag(duration = ANIM_DURATION, numberOfPages = 1)
        else if(binding?.viewPager?.currentItem == 1 && position == 2)
            binding?.viewPager?.fakeDrag(duration = ANIM_DURATION, numberOfPages = 1)
        else if(binding?.viewPager?.currentItem == 2 && position == 1)
            binding?.viewPager?.fakeDrag(leftToRight = false, duration = ANIM_DURATION, numberOfPages = 1)
        else if(binding?.viewPager?.currentItem == 1 && position == 0)
            binding?.viewPager?.fakeDrag(leftToRight = false, duration = ANIM_DURATION, numberOfPages = 1)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    fun ViewPager.fakeDrag(leftToRight: Boolean = true, duration: Long, numberOfPages: Int) {
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
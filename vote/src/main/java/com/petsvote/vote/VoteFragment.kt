package com.petsvote.vote

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.ui.bottomstar.BottomStars
import com.petsvote.vote.databinding.FragmentVoteBinding
import com.petsvote.vote.di.VoteComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class VoteFragment : BaseFragment(R.layout.fragment_vote) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<VoteViewModel.Factory>

    private val ratingComponentViewModel: VoteComponentViewModel by viewModels()
    private val viewModel: VoteViewModel by viewModels {
        viewModelFactory.get()
    }

    private var listVote = mutableListOf(VotePet(-1, -1, "", "", "", "", 0, emptyList()))

    private var binding: FragmentVoteBinding? = null
    private var adapter: VoteAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteBinding.bind(view)

        initAdapter()
        viewModel.getRating()

    }

    private fun initAdapter() {

        adapter = VoteAdapter(listVote, this)
        binding?.pager?.adapter = adapter
        binding?.pager?.isUserInputEnabled = false

        binding?.bottomStars?.mBottomStarsListener = object : BottomStars.BottomStarsListener{
            override fun vote(position: Int) {
                val myFragment =
                    childFragmentManager.findFragmentByTag("f" + binding?.pager?.currentItem)
                myFragment?.tag?.let { it1 -> log(it1) }
                if(myFragment is ItemVoteFragment) {
                    checkCount()
                    myFragment.setRating(position)
                    myFragment.startAnim({ startDragToLeft() })
                }
            }

        }

    }

    private fun checkCount() {
        if((binding?.pager?.currentItem?.plus(5) ?: 0) > (adapter?.itemCount ?: 5)){
            viewModel.getRating()
        }
    }

    fun startDragToLeft(){
        binding?.pager?.fakeDrag(duration = 300L, numberOfPages = 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.pets.collect {
                if(listVote.size <= 1){
                    binding?.pager?.postDelayed( Runnable {
                        initFirst()
                    }, 200)
                }
                listVote.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initFirst(){
        binding?.pager?.setCurrentItem(1, false)
        binding?.pager?.visibility = View.VISIBLE
        binding?.bottomStars?.visibility = View.VISIBLE
        binding?.voteProgress?.visibility = View.GONE
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.voteComponent.inject(this)
    }
}
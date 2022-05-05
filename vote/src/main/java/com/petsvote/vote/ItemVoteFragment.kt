package com.petsvote.vote

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.petsvote.vote.databinding.ItemFragmentVoteBinding

class ItemVoteFragment: Fragment(R.layout.item_fragment_vote) {

    private var binding: ItemFragmentVoteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ItemFragmentVoteBinding.bind(view)

    }

    fun setRating(rating: Int){
        binding?.simpleSFTextView?.text = "+$rating"
    }

    fun startAnim(endAnimation: () -> Unit){
        var motion = binding?.cardMotion
        motion?.transitionToEnd()
        motion?.addTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                endAnimation.invoke()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
    }

}
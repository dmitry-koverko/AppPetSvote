package com.petsvote.vote

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.petsvote.core.ext.getMonthOnYear
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.vote.databinding.ItemFragmentVoteBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val ARG_PARAM = "pet"

class ItemVoteFragment : Fragment(R.layout.item_fragment_vote) {

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ItemVoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param1)
                }
            }
    }

    private var binding: ItemFragmentVoteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ItemFragmentVoteBinding.bind(view)

        var pet: VotePet? =
            if (arguments?.isEmpty == true) null
            else Json.decodeFromString(arguments?.get(ARG_PARAM) as String)

        initView(pet)
    }

    private fun initView(pet: VotePet?) {

        var title = "${pet?.name}, ${pet?.bdate?.let { getMonthOnYear(it) }}"
        val googleText = SpannableStringBuilder("$title *").apply {
            setSpan(
                ImageSpan(
                    requireContext(),
                    if(pet?.sex == 1) com.petsvote.ui.R.drawable.ic_icon_sex_male
                    else com.petsvote.ui.R.drawable.ic_icon_sex_female,
                    ImageSpan.ALIGN_BASELINE
                ), title.length + 1, title.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        binding?.title?.text = googleText
        if (pet?.breed?.isEmpty() == true) pet.breed = getString(com.petsvote.ui.R.string.no_breed)
        var desc =
            if (pet?.location?.isEmpty() == true) "${pet?.breed}" else "${pet?.breed}, ${pet?.location}"
        binding?.description?.text = desc

        if (pet?.photos?.isNotEmpty() == true) {
            binding?.imageView?.list = pet.photos
        }

    }


    fun setRating(rating: Int) {
        binding?.simpleSFTextView?.text = "+$rating"
    }

    fun startAnim(endAnimation: () -> Unit) {
        var motion = binding?.cardMotion
        motion?.transitionToEnd()
        motion?.addTransitionListener(object : MotionLayout.TransitionListener {
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
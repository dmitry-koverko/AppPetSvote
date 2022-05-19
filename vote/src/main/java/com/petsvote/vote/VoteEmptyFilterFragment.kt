package com.petsvote.vote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.petsvote.vote.databinding.FragmentVoteEmptyFilterBinding

private const val ARG_PARAM = "title"

class VoteEmptyFilterFragment: Fragment(R.layout.fragment_vote_empty_filter) {

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VoteEmptyFilterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmentVoteEmptyFilterBinding.bind(view)
    }


    interface VoteEmptyFilterFragmentListener{
        fun clickShare()
    }

}
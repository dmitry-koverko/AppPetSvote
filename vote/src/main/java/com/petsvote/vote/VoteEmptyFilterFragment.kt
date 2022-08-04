package com.petsvote.vote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.petsvote.vote.databinding.FragmentVoteEmptyFilterBinding

private const val ARG_PARAM = "id"

class VoteEmptyFilterFragment: Fragment(R.layout.fragment_vote_empty_filter) {

    var mVoteEmptyFilterFragmentListener: VoteEmptyFilterFragmentListener? = null

    private var binding: FragmentVoteEmptyFilterBinding? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            VoteEmptyFilterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM, param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteEmptyFilterBinding.bind(view)

        binding?.refresh?.setOnClickListener { mVoteEmptyFilterFragmentListener?.refresh() }
        binding?.share?.setOnClickListener { mVoteEmptyFilterFragmentListener?.clickShare() }

        setTitle(arguments?.getInt(ARG_PARAM))
    }

    fun setTitle(type: Int?){
        var title = when(type){
            1 -> getString(com.petsvote.ui.R.string.empty_list_vote)
            2 -> getString(com.petsvote.ui.R.string.empty_list_vote_filter)
            else -> getString(com.petsvote.ui.R.string.empty_list_vote)
        }
        binding?.title?.text = title
    }


    interface VoteEmptyFilterFragmentListener{
        fun clickShare()
        fun refresh()
    }

}
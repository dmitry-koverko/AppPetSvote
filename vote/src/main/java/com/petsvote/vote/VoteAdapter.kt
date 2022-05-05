package com.petsvote.vote

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VoteAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 25

    override fun createFragment(position: Int): Fragment {
        val fragment = ItemVoteFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(ARG_OBJECT, position + 1)
//        }
        return fragment
    }

}
package com.petsvote.vote

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class VoteAdapter(private var listPets: MutableList<VotePet>, fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = listPets.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ItemVoteFragment.newInstance(Json.encodeToString(listPets[position]))
        return fragment
    }

}
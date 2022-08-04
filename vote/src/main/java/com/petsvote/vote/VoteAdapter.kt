package com.petsvote.vote

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class VoteAdapter(private var listPets: MutableList<VotePet>, fragment: VoteFragment,  private val onClick: (VotePet) -> Unit, private val onChange: (Int) -> Unit) : FragmentStateAdapter(fragment),
    ItemVoteFragment.ItemVoteFragmentClick {

    override fun getItemCount(): Int = listPets.size

    override fun createFragment(position: Int): Fragment {

        val fragment =
            if(listPets[position].cardType == 0) {
                onChange(1)
                ItemVoteFragment.newInstance(Json.encodeToString(listPets[position])).apply {
                    itemVoteFragmentClick = this@VoteAdapter
                }
            }
            else {
                onChange(2)
                BonusVoteFragment()
            }
        return fragment
    }

    override fun clickPet(pet: VotePet) {
        onClick(pet)
    }

}
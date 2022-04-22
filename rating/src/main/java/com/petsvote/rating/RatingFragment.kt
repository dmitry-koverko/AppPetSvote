package com.petsvote.rating

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.rating.databinding.FragmentRatingBinding

class RatingFragment: BaseFragment(R.layout.fragment_rating) {

    var binding: FragmentRatingBinding? = null
    private val topAdapter = FingerprintListAdapter(listOf(ItemTopRatingFingerprint()))
    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build(),
        topAdapter,
        //postAdapter
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRatingBinding.bind(view)
        initRatingRecycler()
    }

    private fun initRatingRecycler() {

        binding?.listRating?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = concatAdapter
        }

        binding?.listRating?.postDelayed({
            topAdapter.submitList(mutableListOf<Item>(RatingPet(0, false)))
        }, 300L)


    }

    override fun initObservers() {

    }
}
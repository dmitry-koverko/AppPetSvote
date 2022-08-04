package com.petsvote.vote

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseItemVoteFragment
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.ui.loadImage
import com.petsvote.vote.databinding.FragmentVoteBonusBinding
import com.petsvote.vote.databinding.ItemFragmentVoteBinding
import com.petsvote.vote.di.VoteComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BonusVoteFragment: BaseItemVoteFragment(R.layout.fragment_vote_bonus) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<BonusVoteViewModel.Factory>

    private val ratingComponentViewModel: VoteComponentViewModel by viewModels()
    private val viewModel: BonusVoteViewModel by viewModels {
        viewModelFactory.get()
    }

    var pet: VotePet? = null
    private var binding: FragmentVoteBonusBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteBonusBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            viewModel.getUserPet()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.petImage.collect { binding?.imageView?.loadImage(it) }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.description.collect { binding?.desc?.text = it}
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.voteComponent.injectBonusVote(this)
    }

}
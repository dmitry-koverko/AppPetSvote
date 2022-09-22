package com.petsvote.rating.childFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.rating.R
import com.petsvote.rating.RatingViewModel
import com.petsvote.rating.TopRatingFingerprint
import com.petsvote.rating.databinding.FragmentRatingCollapsingBinding
import com.petsvote.rating.databinding.ItemRatingFragmentBinding
import com.petsvote.rating.di.RatingComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

abstract class BaseChildFragment: BaseFragment(R.layout.item_rating_fragment) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<RatingViewModel.Factory>

    private val ratingComponentViewModel: RatingComponentViewModel by viewModels()
    private val viewModel: RatingViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: ItemRatingFragmentBinding? = null
    private var ratingAdapter = FingerprintListAdapter(listOf(TopRatingFingerprint(::onClickPet)))
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getRatingMore(0)
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.ratingList.collect {list ->
                list?.let { ratingAdapter.submitList(it) }
            }
        }
    }

    private fun initRecycler(){
        var manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }
        binding?.listRating?.apply {
            layoutManager = manager
            adapter = ratingAdapter
        }

        binding?.listRating?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                log("new state = $newState")
            }

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: GridLayoutManager =
                    recyclerView.getLayoutManager() as GridLayoutManager

                if(dy > 0){
                    if (!isLoading) {

                        if (linearLayoutManager != null && ratingAdapter.itemCount != 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() in ratingAdapter.itemCount - 25..ratingAdapter.itemCount - 24) {
                            //bottom of list!
                            loadMore()
//                            viewModel.getRatingMore(
//                                (ratingAdapter.currentList.last() as? RatingPet)?.index ?: 0
//                            )
//                            isLoading = true
                        }
                    }
                }
            }
        })
    }

    abstract fun loadMore()

    private fun onClickPet(item: RatingPet) {
//        var bundle = Bundle()
//        bundle.putInt("pet", item.pet_id)
//        bundle.putInt("petBreed", item.breed_id)
//        bundle.putString("petKind", item.type)
//        bundle.putInt("userId", item.user_id)
//        activity?.let { navigation.startActivityPetInfo(it, bundle) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.ratingComponent.inject(this)
    }
}
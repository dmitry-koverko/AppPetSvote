package com.petsvote.rating

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.rating.databinding.FragmentRatingCollapsingBinding
import com.petsvote.rating.di.RatingComponentViewModel
import com.petsvote.rating.fingerprints.FindPetFingerprint
import com.petsvote.rating.fingerprints.UserPetFingerprint
import com.petsvote.ui.ext.hide
import com.petsvote.ui.ext.show
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RatingFragment : BaseFragment(R.layout.fragment_rating_collapsing) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<RatingViewModel.Factory>

    private val ratingComponentViewModel: RatingComponentViewModel by viewModels()
    private val viewModel: RatingViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: FragmentRatingCollapsingBinding? = null
    private val ratingAdapter = FingerprintPagingAdapter(listOf(TopRatingFingerprint()))
    private val findPetAdapter = FingerprintListAdapter(listOf(FindPetFingerprint(::onFindPet)))
    private val userPetsAdapter = FingerprintListAdapter(listOf(UserPetFingerprint(::onClickUserPet)))
    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build(),
        findPetAdapter,
        userPetsAdapter
    )

    private var topLinearHeight = 0
    private var check_ScrollingUp = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRatingCollapsingBinding.bind(view)

        initRatingRecycler()
        initBottomRecycler()

        lifecycleScope.launchWhenStarted {
            viewModel.getRating()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getUserPets()
        }
    }

    private fun initBottomRecycler() {
        binding?.listPetsUser?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = concatAdapter
        }
        findPetAdapter.submitList(listOf(SimpleItem()))

        binding?.scrollToTop?.setOnClickListener {
            binding?.listRating?.postDelayed(
                Runnable { binding?.listRating?.scrollToPosition(0) },
                10
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRatingRecycler() {
        //binding?.refresh?.setColorSchemeResources(com.petsvote.ui.R.color.progress_bar)
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

        binding?.linearHeader?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (topLinearHeight == 0)
                    topLinearHeight = binding?.linearHeader?.measuredHeight ?: 0
                binding?.linearHeader?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        binding?.listRating?.setOnScrollChangeListener(
            (View.OnScrollChangeListener { p0, scrollX, scrollY, oldScrollX, oldScrollY ->
                //if (isAnim) return@OnScrollChangeListener
                var offset = binding?.listRating?.computeVerticalScrollOffset()
                offset?.let {
                    log("measureHeight = $offset")

                    if (scrollY < oldScrollY) {
                        if (check_ScrollingUp) {
                            binding?.bottomBar?.startAnimation(
                                AnimationUtils.loadAnimation(
                                    context,
                                    com.petsvote.ui.R.anim.trans_downwards
                                ))
                            binding?.scrollToTop?.visibility = View.VISIBLE
                            check_ScrollingUp = false;
                        }
                    } else if (offset > 10) {
                        if (!check_ScrollingUp) {
                            binding?.bottomBar?.startAnimation(
                                AnimationUtils.loadAnimation(
                                    context,
                                    com.petsvote.ui.R.anim.trans_upwards
                                )
                            )
                            check_ScrollingUp = true;

                        }
                    }
                }
            })
        )

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.pages.collect {
                it?.let { page ->
                    ratingAdapter.submitData(page)
                }
            }
        }

         lifecycleScope.launchWhenResumed {
             viewModel.userPets.collect {
                 for (i in 0 until it.size){
                     if(i == 0){
                         it[0].isClickPet = true
                     }
                     it[i].position = i
                 }
                 userPetsAdapter.submitList(it)
             }
         }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.ratingComponent.inject(this)
    }

    private fun onFindPet(item: SimpleItem) {
        log("click findPet")
    }
    private fun onClickUserPet(clickItem: UserPet) {
        log("click findPet")
        log(userPetsAdapter.currentList.toString())
        var newList = userPetsAdapter.currentList
        newList.onEach { item: Item? ->  (item as UserPet).isClickPet = false}
        (newList.find { (it as UserPet).pets_id == clickItem.pets_id} as UserPet).isClickPet = true
        log(newList.toString())
        userPetsAdapter.notifyDataSetChanged()
    }

}
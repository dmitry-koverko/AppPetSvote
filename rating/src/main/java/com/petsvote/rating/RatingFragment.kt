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
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.rating.databinding.FragmentRatingCollapsingBinding
import com.petsvote.rating.di.RatingComponentViewModel
import com.petsvote.rating.fingerprints.FindPetFingerprint
import com.petsvote.rating.fingerprints.UserPetFingerprint
import com.petsvote.ui.ext.hide
import com.petsvote.ui.ext.show
import dagger.Lazy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Runnable
import javax.inject.Inject

class RatingFragment : BaseFragment(R.layout.fragment_rating_collapsing) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<RatingViewModel.Factory>

    private val ratingComponentViewModel: RatingComponentViewModel by viewModels()
    private val viewModel: RatingViewModel by viewModels {
        viewModelFactory.get()
    }

    private var fragmentScope = CoroutineScope(Dispatchers.Main + Job())

    var binding: FragmentRatingCollapsingBinding? = null
    private var ratingAdapter = FingerprintPagingAdapter(listOf(TopRatingFingerprint()))
    private val findPetAdapter = FingerprintListAdapter(listOf(FindPetFingerprint(::onFindPet)))
    private val userPetsAdapter =
        FingerprintListAdapter(listOf(UserPetFingerprint(::onClickUserPet)))
    private var config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false)
        .build()
    private var concatAdapter = ConcatAdapter(
        config,
        findPetAdapter,
        userPetsAdapter
    )

    private var topLinearHeight = 0
    private var check_ScrollingUp = false
    private var currentClickUserPetId = 0


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

        lifecycleScope.launchWhenStarted {
            viewModel.getRatingFilter()
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
                    if (scrollY < oldScrollY) {
                        if (check_ScrollingUp) {
                            binding?.bottomBar?.startAnimation(
                                AnimationUtils.loadAnimation(
                                    context,
                                    com.petsvote.ui.R.anim.trans_downwards
                                )
                            )
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
                    var list = ratingAdapter.snapshot().items
                    if (list.isNotEmpty()) {
                        var myPet = (list.find { (it as RatingPet).pet_id == currentClickUserPetId })
                        if ((ratingAdapter.snapshot().items.first() as RatingPet).index != 1
                            && myPet != null && list.size <= 50
                        ) {
                            log("click is my pet")
                            var first = (list[0] as RatingPet)
                            var my =
                                (list.find { (it as RatingPet).pet_id == currentClickUserPetId })
                            my?.let {
                                log(first.toString())
                                log(it.toString())
                                var deff = (it as RatingPet).index - first.index
                                log(deff.toString())
                                var line = deff / 2
                                var position = line * (resources.displayMetrics.heightPixels * 0.36)
                                log(position.toString())
                                binding?.listRating?.postDelayed(
                                    Runnable { binding?.listRating?.scrollToPosition(position.toInt()) },
                                    1000
                                )
                                currentClickUserPetId = -1
                            }

                        }
                    }

                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.userPets.collect {
                for (i in 0 until it.size) {
                    if (i == 0) {
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
        clickItem.pets_id?.let { currentClickUserPetId = it }
        log("click findPet")
        log(userPetsAdapter.currentList.toString())
        var newList = userPetsAdapter.currentList
        newList.onEach { item: Item? -> (item as UserPet).isClickPet = false }
        (newList.find { (it as UserPet).pets_id == clickItem.pets_id } as UserPet).isClickPet = true
        log(newList.toString())
        userPetsAdapter.notifyDataSetChanged()

        clickItem.id?.let { viewModel.setBreedId(it) }
        ratingAdapter.refresh()
    }
}
package com.petsvote.rating

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.dialog.UserLocationDialog
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.flow.ratingUpdate
import com.petsvote.navigation.MainNavigation
import com.petsvote.rating.databinding.FragmentRatingCollapsingBinding
import com.petsvote.rating.di.RatingComponentViewModel
import com.petsvote.rating.fingerprints.FindPetFingerprint
import com.petsvote.rating.fingerprints.UserPetFingerprint
import com.petsvote.ui.ext.hide
import com.petsvote.ui.ext.show
import com.petsvote.ui.maintabs.BesieTabLayout
import com.petsvote.ui.maintabs.BesieTabLayoutSelectedListener
import com.petsvote.ui.maintabs.BesieTabSelected
import dagger.Lazy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import me.vponomarenko.injectionmanager.x.XInjectionManager
import java.lang.Runnable
import javax.inject.Inject

class RatingFragment : BaseFragment(R.layout.fragment_rating_collapsing) {

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<RatingViewModel.Factory>

    private val ratingComponentViewModel: RatingComponentViewModel by viewModels()
    private val viewModel: RatingViewModel by viewModels {
        viewModelFactory.get()
    }

    private var fragmentScope = CoroutineScope(Dispatchers.Main + Job())

    var binding: FragmentRatingCollapsingBinding? = null
    private var ratingAdapter = FingerprintPagingAdapter(listOf(TopRatingFingerprint(::onClickPet)))
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
    private var currentClickUserPetId = -1


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRatingCollapsingBinding.bind(view)

        initSwipeRefresh()
        initRatingRecycler()
        initBottomRecycler()
        initTabs()
        initFilter()

        lifecycleScope.launchWhenStarted {
            viewModel.resetBreedId()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getRating()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getUserPets()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getRatingFilter()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getRatingFilterType()
        }

        lifecycleScope.launchWhenResumed {
            viewModel.checkLocation()
        }
    }

    private fun initFilter() {
        binding?.imageFilter?.setOnClickListener {
            activity?.let { it1 -> navigation.startFilterActivityForResult(it1) }
        }
    }

    private fun initTabs() {
        binding?.tabs?.setTabSelectedListener(object : BesieTabLayoutSelectedListener{
            override fun selected(tab: BesieTabSelected) {
                if(tab == BesieTabSelected.NullUserLocation){
                    UserLocationDialog().show(childFragmentManager, UserLocationDialog.TAG)
                    return
                }else {
                    viewModel.setFilterType(tab)
                    refresh()
                }
            }
        })
    }

    private fun initSwipeRefresh() {
        binding?.swipeRefresh?.setColorSchemeResources(com.petsvote.ui.R.color.ui_primary)
        binding?.swipeRefresh?.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                swipeRefresh()
            }
        })
    }

    private fun initBottomRecycler() {
        binding?.listPetsUser?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = concatAdapter
        }
        findPetAdapter.submitList(listOf(SimpleItem()))

        binding?.scrollToTop?.setOnClickListener {
            viewModel.resetBreedId()
            refresh()
//            binding?.listRating?.postDelayed(
//                Runnable { binding?.listRating?.scrollToPosition(0) },
//                10
//            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRatingRecycler() {
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

        ratingAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (currentClickUserPetId != -1) {
                    var list = ratingAdapter.snapshot().items
                    var myPet = (list.find { (it as RatingPet).pet_id == currentClickUserPetId })
                    if(myPet != null){
                        var first = (list[0] as RatingPet)
                        var deff = (myPet as RatingPet).index - first.index
                        binding?.listRating?.smoothScrollToPosition(deff + 2)
                        currentClickUserPetId = -1
                        binding?.progressBar?.visibility = View.GONE
                    }
                }
            }
        })

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.filterText.collect {
                it?.let { textFilter ->
                   binding?.filterText?.text = textFilter
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.pages.collect {
                it?.let { page ->
                    ratingAdapter.submitData(page)
                    binding?.progressBar?.visibility = View.GONE
                    binding?.swipeRefresh?.isRefreshing = false
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

        lifecycleScope.launchWhenStarted {
            viewModel.filterType.collect {
                when(it){
                    RatingFilterType.GLOBAL -> binding?.tabs?.initWorldTab()
                    RatingFilterType.COUNTRY -> binding?.tabs?.initCountryTabs()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLocationUser.collect {
                binding?.tabs?.isUserLocation = it
            }
        }

        lifecycleScope.launchWhenStarted {
            ratingUpdate.collect {
                it?.let {
                    viewModel.resetBreedId()
                    ratingUpdate.emit(null)
                    swipeRefresh()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.ratingComponent.inject(this)
    }

    private fun onFindPet(item: SimpleItem) {
        activity?.let { navigation.startActivityFindPet(it) }
    }

    private fun onClickPet(item: RatingPet) {
        var bundle = Bundle()
        bundle.putInt("pet", item.pet_id)
        bundle.putInt("petBreed", item.breed_id)
        bundle.putString("petKind", item.type)
        bundle.putInt("userId", item.user_id)
        activity?.let { navigation.startActivityPetInfo(it, bundle) }
    }

    private fun onClickUserPet(clickItem: UserPet) {
        fragmentScope.launch { ratingAdapter.submitData(PagingData.from(listOf())) }
        binding?.listRating?.postDelayed(
            Runnable { binding?.listRating?.scrollToPosition(0) },
            10
        )
        clickItem.pets_id?.let { currentClickUserPetId = it }
        var newList = userPetsAdapter.currentList
        newList.onEach { item: Item? -> (item as UserPet).isClickPet = false }
        (newList.find { (it as UserPet).pets_id == clickItem.pets_id } as UserPet).isClickPet = true
        userPetsAdapter.notifyDataSetChanged()
        clickItem.id?.let { viewModel.setBreedId(it) }
        refresh()

    }

    private fun refresh(){
        fragmentScope.launch { ratingAdapter.submitData(PagingData.from(listOf())) }
        ratingAdapter.refresh()
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun swipeRefresh(){
        viewModel.resetBreedId()
        fragmentScope.launch { ratingAdapter.submitData(PagingData.from(listOf())) }
        ratingAdapter.refresh()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle = Bundle()) =
            RatingFragment().apply {
                arguments = bundle
            }
    }
}
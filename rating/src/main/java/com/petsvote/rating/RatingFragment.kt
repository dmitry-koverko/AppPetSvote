package com.petsvote.rating

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.rating.databinding.FragmentRatingBinding
import com.petsvote.rating.databinding.FragmntRtBinding
import com.petsvote.rating.di.RatingComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RatingFragment : BaseFragment(R.layout.fragmnt_rt), MotionLayout.TransitionListener {

    @Inject
    internal lateinit var viewModelFactory: Lazy<RatingViewModel.Factory>

    private val ratingComponentViewModel: RatingComponentViewModel by viewModels()
    private val viewModel: RatingViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: FragmntRtBinding? = null
    private val ratingAdapter = FingerprintPagingAdapter(listOf(TopRatingFingerprint()))

    private var topLinearHeight = 0
    private var currentState = ViewStateFragment.DEFAULT
    private var isAnim = false



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmntRtBinding.bind(view)
        initRatingRecycler()
        lifecycleScope.launchWhenStarted {
            viewModel.getRating()
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

        var motion = binding?.topMotion
        motion?.addTransitionListener(this)

//        binding?.listRating?.setOnScrollChangeListener(
//            (View.OnScrollChangeListener { p0, scrollX, scrollY, oldScrollX, oldScrollY ->
//                if(isAnim) return@OnScrollChangeListener
//                var offset = binding?.listRating?.computeVerticalScrollOffset()
//                offset?.let {
//                    log("current state = ${currentState.name}")
//                    if (scrollY < oldScrollY) {
//                        when(currentState){
//                            ViewStateFragment.HIDE_ALL -> {
//                                if(offset >= topLinearHeight + 50){
//                                    motion?.setTransition(
//                                        R.id.hideAllState,
//                                        R.id.showHeaderAndFooter
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.SHOW_FILTER_AND_FOOTER
//                                }else if (offset >= 50 && offset <= topLinearHeight) {
//                                    motion?.setTransition(
//                                        R.id.hideAllState,
//                                        R.id.showHeaderAndFooter
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.SHOW_FILTER_AND_FOOTER
//                                }else {
//                                    motion?.setTransition(
//                                        R.id.showHeaderAndFooter,
//                                        R.id.defaultState
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.DEFAULT
//                                }
//                            }
//                            ViewStateFragment.SHOW_FILTER_AND_FOOTER -> {
//                                if (offset < topLinearHeight) {
//                                    motion?.setTransition(
//                                        R.id.showHeaderAndFooter,
//                                        R.id.defaultState
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.DEFAULT
//                                }
//                            }
//                            ViewStateFragment.HIDE_HEADER_AND_FOOTER -> {
//                                if (offset <= 0) {
//                                    motion?.setTransition(
//                                        R.id.hideHeaderAndFooterState,
//                                        R.id.defaultState
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.DEFAULT
//                                }
//                            }
//                        }
//
//                    } else {
//                        when (currentState) {
//                            ViewStateFragment.DEFAULT -> {
//                                if (offset > 70) {
//                                    motion?.setTransition(
//                                        R.id.defaultState,
//                                        R.id.hideHeaderAndFooterState
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.HIDE_HEADER_AND_FOOTER
//                                }
//                            }
//                            ViewStateFragment.HIDE_HEADER_AND_FOOTER -> {
//                                if (offset >= topLinearHeight) {
//                                    motion?.setTransition(
//                                        R.id.hideHeaderAndFooterState,
//                                        R.id.hideAllState
//                                    )
//                                    motion?.transitionToEnd()
//                                    currentState = ViewStateFragment.HIDE_ALL
//                                }
//                            }
//
//                            ViewStateFragment.SHOW_FILTER_AND_FOOTER -> {
//                                motion?.setTransition(
//                                    R.id.showHeaderAndFooter,
//                                    R.id.hideAllState
//                                )
//                                motion?.transitionToEnd()
//                                currentState = ViewStateFragment.HIDE_ALL
//                            }
//                        }
//                    }
//                }
//            })
//        )

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.pages.collect {
                it?.let {page ->
                    ratingAdapter.submitData(page)
                }
            }
        }
    }


    enum class ViewStateFragment {
        DEFAULT,
        HIDE_HEADER_AND_FOOTER,
        HIDE_ALL,
        SHOW_FILTER_AND_FOOTER
    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
        isAnim = true
    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        isAnim = false
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.ratingComponent.inject(this)
    }

}
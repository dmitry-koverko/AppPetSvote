package com.petsvote.vote

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.flow.findPetToVote
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.bottomstar.BottomStars
import com.petsvote.ui.ext.hideAlpha
import com.petsvote.ui.ext.showAlpha
import com.petsvote.ui.shareApp
import com.petsvote.vote.databinding.FragmentVoteBinding
import com.petsvote.vote.di.VoteComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.vponomarenko.injectionmanager.x.XInjectionManager
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class VoteFragment : BaseFragment(R.layout.fragment_vote),
    VoteEmptyFilterFragment.VoteEmptyFilterFragmentListener,
    ItemVoteFragment.ItemVoteFragmentClick {


    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<VoteViewModel.Factory>

    private val ratingComponentViewModel: VoteComponentViewModel by viewModels()
    private val viewModel: VoteViewModel by viewModels {
        viewModelFactory.get()
    }

    private var listVote = mutableListOf<VotePet>()
    private var emptypet = VotePet(-1, -1, -1,"", "", 0, "", "", -1,"", 0, 0,emptyList())

    private var binding: FragmentVoteBinding? = null
    private var adapter: VoteAdapter? = null

    private val myFragment = VoteEmptyFilterFragment.newInstance(2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteBinding.bind(view)

        initAdapter()
        viewModel.getRating()

        commitEmptyFragment()

    }


    private fun initAdapter() {
        binding?.voteProgress?.visibility = View.VISIBLE
        viewModel.resetList()
        listVote.clear()
        if(adapter != null) adapter = null
        adapter = VoteAdapter(listVote, this, this::clickPet, this::onChange)
        binding?.pager?.adapter = adapter
        binding?.pager?.isUserInputEnabled = false

        binding?.bottomStars?.mBottomStarsListener = object : BottomStars.BottomStarsListener{
            override fun vote(position: Int) {
                val myFragment =
                    childFragmentManager.findFragmentByTag("f" + binding?.pager?.currentItem)
                if(myFragment is ItemVoteFragment) {
                    checkCount()
                    myFragment.setRating(position)
                    myFragment.startAnim({ startDragToLeft() })
                }
            }

        }

        binding?.next?.setOnClickListener {
            startDragToLeft()
        }
    }

    private fun checkCount() {
        log("currentItem = ${binding?.pager?.currentItem}")
        log("itemCount = ${adapter?.itemCount}")
        if((adapter?.itemCount ?: 2) < (binding?.pager?.currentItem?.plus(3) ?: 1)){
            viewModel.getRating()
            log("get data without size")
        }
//        if(binding?.pager?.currentItem?.plus(1)?.equals(adapter?.itemCount) == true){
//
//        }
        if(binding?.pager?.currentItem?.plus(1) == adapter?.itemCount){
            showDialog(1)
        }
    }


    // 1- emptyVote, 2 - emptyByFilter
    private fun showDialog(type: Int){
        log("show dialog type = $type")
        Timer().schedule(1000){
            activity?.runOnUiThread{
                myFragment.setTitle(type)
                binding?.container?.showAlpha()
            }
        }
    }

    private fun commitEmptyFragment(){
        if(myFragment.isAdded()) { return; }
        childFragmentManager.commit {
            myFragment.mVoteEmptyFilterFragmentListener = this@VoteFragment
            add(R.id.container, myFragment)
            setReorderingAllowed(true)
        }
    }

    fun startDragToLeft(){
        binding?.pager?.fakeDrag(duration = 500L, numberOfPages = 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.pets.collect {
                log(" ---------------- get DATA ---------------")
                if(it != null){
                    log("listVote.size = ${listVote.size}")
                    log("listVote.size = $it")
                    if(listVote.size == 0 && it.isNotEmpty()){
                        log("init adapter ")
                        listVote.add(emptypet)
                        binding?.pager?.postDelayed( Runnable {
                            initFirst()
                        }, 200)
                        if(it[0].cardType == 1) binding?.next?.visibility = View.VISIBLE
                        else binding?.next?.visibility = View.GONE
                    }
                    if(it.isNotEmpty()){
                        log("init not empty")
                        binding?.container?.hideAlpha()
                        listVote.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }

                    if(it.isEmpty() && listVote.size == 0) {
                        log("showDialog")
                        showDialog(2)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            findPetToVote.collect { votePet ->
                votePet?.let {
                    listVote.add(0, votePet)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initFirst(){
        binding?.pager?.setCurrentItem(1, false)
        binding?.pager?.visibility = View.VISIBLE
        binding?.bottomStars?.visibility = View.VISIBLE
        binding?.voteProgress?.visibility = View.GONE
    }

    fun ViewPager2.fakeDrag(leftToRight: Boolean = true, duration: Long, numberOfPages: Int) {
        val pxToDrag: Int = this.width
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            var currentPxToDrag: Float = (currentValue - previousValue).toFloat() * numberOfPages
            when {
                leftToRight -> {
                    currentPxToDrag *= -1
                }
            }
            this.fakeDragBy(currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { this@fakeDrag.beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { this@fakeDrag.endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
        })
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ratingComponentViewModel.voteComponent.inject(this)
    }

    override fun clickShare() {
        shareApp()
    }

    override fun refresh() {
        log("refresh data")
        viewModel.getRating()
        binding?.pager?.visibility = View.GONE
        binding?.bottomStars?.visibility = View.GONE
        binding?.voteProgress?.visibility = View.VISIBLE
        listVote.clear()
        binding?.container?.hideAlpha()

    }

    override fun clickPet(pet: VotePet) {
        var bundle = Bundle()
        bundle.putInt("pet", pet.pet_id)
        bundle.putInt("petBreedId", pet.breed_id)
        bundle.putString("petKind", pet.type)
        bundle.putInt("userId", pet.user_id)
        activity?.let { navigation.startActivityPetInfo(it, bundle) }
    }


    private fun onChange(type: Int) {
        if (type == 1) {
            binding?.bottomStars?.visibility = View.VISIBLE
            binding?.next?.visibility = View.GONE
        } else {
            binding?.next?.visibility = View.VISIBLE
            binding?.bottomStars?.visibility = View.GONE
        }
    }

}
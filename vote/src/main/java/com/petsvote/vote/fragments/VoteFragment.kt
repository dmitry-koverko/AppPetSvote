package com.petsvote.vote.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.bottomstar.BottomStars
import com.petsvote.ui.ext.showAlpha
import com.petsvote.ui.shareApp
import com.petsvote.vote.R
import com.petsvote.vote.viewmodels.VoteViewModel
import com.petsvote.vote.adapters.VoteAdapter
import com.petsvote.vote.databinding.FragmentVoteBinding
import com.petsvote.vote.di.VoteComponentViewModel
import com.petsvote.vote.entity.VoteState
import com.petsvote.vote.fragments.state.ItemVoteFragment
import com.petsvote.vote.fragments.state.VoteEmptyFilterFragment
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.vponomarenko.injectionmanager.x.XInjectionManager
import java.util.*
import javax.inject.Inject

class VoteFragment : BaseFragment(R.layout.fragment_vote),
    VoteEmptyFilterFragment.VoteEmptyFilterFragmentListener,
    ItemVoteFragment.ItemVoteFragmentClick {


    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    var fragmentVoteCurrent: ItemVoteFragment? = null
    var fragmentVoteNext: ItemVoteFragment? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<VoteViewModel.Factory>

    private val ratingComponentViewModel: VoteComponentViewModel by viewModels()
    private val viewModel: VoteViewModel by viewModels {
        viewModelFactory.get()
    }

    private var listVote = mutableListOf<VotePet>()
    private var emptypet = VotePet(-1, -1, -1,"", "", 0, "", "", -1,"", 0, 0,emptyList())

    private var binding: FragmentVoteBinding? = null

    private val myFragment = VoteEmptyFilterFragment.newInstance(2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVoteBinding.bind(view)

        commitEmptyFragment()
        initAdapter()
        viewModel.getRatingFirst()
    }


    fun fragmentTransactionDetails(votePet: VotePet) {
         if(fragmentVoteCurrent == null && fragmentVoteNext == null){
             fragmentVoteCurrent = ItemVoteFragment.newInstance(Json.encodeToString(votePet))
             activity?.supportFragmentManager?.commit {
                 setReorderingAllowed(true)
                 fragmentVoteCurrent?.let { replace(R.id.frameList, it) }
             }
         }else {
             fragmentVoteNext = ItemVoteFragment.newInstance(Json.encodeToString(votePet))
             activity?.supportFragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(com.petsvote.ui.R.anim.to_term, com.petsvote.ui.R.anim.from_term, com.petsvote.ui.R.anim.to_term, com.petsvote.ui.R.anim.from_term_info)
                 fragmentVoteCurrent?.let { remove(it) }
                add(fragmentVoteNext!!, "g")
                replace(R.id.frameList, fragmentVoteNext!!)
                addToBackStack("r")
                commit().apply {
                    fragmentVoteCurrent = fragmentVoteNext
                }
            }
         }
//        childFragmentManager.beginTransaction().apply {
//            setCustomAnimations(com.petsvote.ui.R.anim.trans_up, com.petsvote.ui.R.anim.trans_down)
//            add(fr, "ed")
//            commit()
//        }
        /*activity?.supportFragmentManager?.commit {
            //setCustomAnimations(com.petsvote.ui.R.anim.to_term, com.petsvote.ui.R.anim.from_term)
            setReorderingAllowed(true)
            fragmentVoteCurrent?.let { replace(R.id.frameList, it) }
        }*/


//        childFragmentManager.beginTransaction().apply {
//            setCustomAnimations(com.petsvote.ui.R.anim.trans_down, com.petsvote.ui.R.anim.trans_downwards, com.petsvote.ui.R.anim.trans_up, com.petsvote.ui.R.anim.trans_upwards)
//            remove(fr1)
//            add(fr, "fr")
//            replace(R.id.frameList, fr)
//            addToBackStack("fdsfsdf")
//            commit()
//        }
    }

    fun replace(){
//        activity?.supportFragmentManager?.beginTransaction()?.apply {
//            setCustomAnimations(com.petsvote.ui.R.anim.to_term, com.petsvote.ui.R.anim.from_term, com.petsvote.ui.R.anim.to_term, com.petsvote.ui.R.anim.from_term_info)
//            remove(fr)
//            add(fr1, "g")
//            replace(R.id.frameList, fr1)
//            addToBackStack("r")
//            commit()
//        }
    }

    private fun initAdapter() {
        binding?.voteProgress?.visibility = View.VISIBLE
        viewModel.resetList()
        binding?.bottomStars?.mBottomStarsListener = object : BottomStars.BottomStarsListener{
            override fun vote(position: Int) {
//                val myFragment =
//                    childFragmentManager.findFragmentByTag("f" + binding?.pager?.currentItem)
//                if(myFragment is ItemVoteFragment) {
//                    myFragment.setRating(position)
//                    myFragment.startAnim({ viewModel.next() })
//                }
                fragmentVoteCurrent?.setRating(position)
                fragmentVoteCurrent?.startAnim({ viewModel.next() })
            }

        }

        binding?.next?.setOnClickListener {
            viewModel.next()
        }
    }

    // 1- emptyVote, 2 - emptyByFilter
    private fun showDialog(type: Int){
        activity?.runOnUiThread{
            myFragment.setTitle(type)
            binding?.container?.showAlpha()
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

    @SuppressLint("NotifyDataSetChanged")
    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.pets.collect { votePet ->
                votePet?.let {
                    log("VoteFragment: add pet = ${it.name}")
                    fragmentTransactionDetails(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                log("VoteFragment: state = ${it.toString()}")
                when(it){
                    VoteState.Loading -> loading()
                    VoteState.ErrorFilter -> errorFilterState()
                    VoteState.ErrorNoPet -> errorNoPet()
                    VoteState.VoteDefault -> defaultState()
                    VoteState.VoteBonus -> voteBonusState()
                }
            }
        }

        /*lifecycleScope.launchWhenStarted {
            viewModel.pets.collect {listVoteNow ->
                log(" ---------------- get DATA ---------------")
                if(listVoteNow != null){
                    log("listVote.size = ${listVote.size}")
                    log("listVote.size = $listVoteNow")
                    if(listVote.size == 0 && listVoteNow.isNotEmpty()){
                        log("init adapter ")
                        listVote.add(emptypet)
                        binding?.pager?.postDelayed( Runnable {
                            initFirst()
                        }, 200)
                        if(listVoteNow[0].cardType == 1) binding?.next?.visibility = View.VISIBLE
                        else binding?.next?.visibility = View.GONE
                    }
                    if(listVoteNow.isNotEmpty()){
                        log("init not empty")
                        binding?.container?.hideAlpha()
                        listVote.addAll(listVoteNow.filter { it.cardType == 2 || it.cardType == 0})
                        adapter?.notifyDataSetChanged()
                    }

                    if(listVoteNow.isEmpty() && listVote.size == 0) {
                        log("showDialog")
                        showDialog(2)
                    }
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            findPetToVote.collect { votePet ->
                votePet?.let {
                    listVote.clear()
                    listVote.add(0, votePet)
                    adapter?.notifyDataSetChanged()
                    binding?.pager?.postDelayed( Runnable {
                        initFirst()
                    }, 200)
                    findPetToVote.emit(null)
                }
            }
        }*/
    }

    private fun voteBonusState() {
        binding?.bottomStars?.visibility = View.GONE
        binding?.frameList?.visibility = View.VISIBLE
        binding?.next?.visibility = View.VISIBLE
        binding?.voteProgress?.visibility = View.GONE
        binding?.container?.visibility = View.GONE
    }

    private fun defaultState() {
        binding?.bottomStars?.visibility = View.VISIBLE
        binding?.frameList?.visibility = View.VISIBLE
        binding?.next?.visibility = View.GONE
        binding?.voteProgress?.visibility = View.GONE
        binding?.container?.visibility = View.GONE
    }

    private fun errorNoPet() {
        showDialogError(1)
    }

    private fun errorFilterState() {
        showDialogError(2)
    }

    private fun showDialogError(type: Int){
        binding?.frameList?.visibility = View.GONE
        binding?.next?.visibility = View.GONE
        binding?.bottomStars?.visibility = View.GONE
        binding?.voteProgress?.visibility = View.GONE
        showDialog(type)
    }

    private fun loading() {
        binding?.frameList?.visibility = View.GONE
        binding?.container?.visibility = View.GONE
        binding?.next?.visibility = View.GONE
        binding?.bottomStars?.visibility = View.GONE
        binding?.voteProgress?.visibility = View.VISIBLE
    }

    private fun initFirst(){
//        binding?.pager?.setCurrentItem(1, false)
//        binding?.pager?.visibility = View.VISIBLE
//        binding?.bottomStars?.visibility = View.VISIBLE
//        binding?.voteProgress?.visibility = View.GONE
    }

    fun ViewPager2.fakeDrag(leftToRight: Boolean = true, duration: Long, numberOfPages: Int) {

        listVote.add(0, emptypet)
        adapter?.notifyDataSetChanged()

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
            override fun onAnimationEnd(animation: Animator?) {
                this@fakeDrag.endFakeDrag()
                log("VoteFragment: add pet fake drag = ${listVote}")
                listVote.removeFirst()

                log("VoteFragment: add pet list after remove = ${listVote}")
                if(listVote.size == 2) {}//listVote.removeAt(0)
                else if(listVote.size == 3){
                    //listVote.removeAt(0)
                    //listVote.removeAt(0)
                    //log("VoteFragment: add pet = ${listVote}")
                    //adapter?.notifyDataSetChanged()
                }
            }
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
        viewModel.getRatingReLoad()
    }

    override fun clickPet(pet: VotePet) {
//        var bundle = Bundle()
//        bundle.putInt("pet", pet.pet_id)
//        bundle.putInt("petBreedId", pet.breed_id)
//        bundle.putString("petKind", pet.type)
//        bundle.putInt("userId", pet.user_id)
//        activity?.let { navigation.startActivityPetInfo(it, bundle) }
    }

    private fun onChange(type: Int) {
//        if (type == 1) {
//            binding?.bottomStars?.visibility = View.VISIBLE
//            binding?.next?.visibility = View.GONE
//        } else {
//            binding?.next?.visibility = View.VISIBLE
//            binding?.bottomStars?.visibility = View.GONE
//        }
    }

}
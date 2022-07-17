package com.petswote.pet.find

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.getMonthOnYear
import com.petsvote.ui.BesieKeyboard
import com.petsvote.ui.SearchBar
import com.petsvote.ui.bottomstar.BottomStars
import com.petsvote.ui.ext.disabled
import com.petsvote.ui.ext.enabled
import com.petsvote.ui.loadImage
import com.petswote.pet.R
import com.petswote.pet.databinding.FragmentFindPetBinding
import com.petswote.pet.di.PetComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FindPetFragment: BaseFragment(R.layout.fragment_find_pet) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<FindPetViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: FindPetViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentFindPetBinding? = null

    private var text = ""
    private var isShowKeyboard = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFindPetBinding.bind(view)

        binding?.findContainer?.setOnClickListener {
            if(text.isNotEmpty() && text.length >=9){
                var rInt: Int? = text.replace(" ", "").toIntOrNull()
                rInt?.let {
                    viewModel.findPet(it)
                }
            }//72160874 // 36268011
        }

        binding?.close?.setOnClickListener { activity?.finish() }

        binding?.searchBar?.textHint = "0000 0000"
        binding?.searchBar?.editType = SearchBar.SearchBarType.PET_ID
        binding?.searchBar?.edit?.setOnTouchListener { p0, p1 ->
            val inType: Int = binding?.searchBar?.edit!!.getInputType() // backup the input type
            binding?.searchBar?.edit!!.setInputType(InputType.TYPE_NULL) // disable soft input
            binding?.searchBar?.edit!!.onTouchEvent(p1) // call native handler
            binding?.searchBar?.edit!!.setInputType(inType) // restore input type
            true
        }
        binding?.keyboard?.setOnKeyboardListener(object : BesieKeyboard.BesieKeyboardListener{
            override fun click(value: Int) {
                if(text.length == 9 && value != -1) return
                if(value != -1){
                    text = "$text$value"
                }else {
                    if(text.isNotEmpty()){
                        text = text.substring(0, text.length -1 )
                    }
                }
                binding?.searchBar?.textSearch = text
            }

        })

        binding?.searchBar?.setOnTextSearchBar(object : SearchBar.OnTextSearchBar{
            override fun onText(txt: String) {
                text = txt

                if(text.length == 9 ) {
                    binding?.findContainer?.enabled()
                    binding?.find?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_selected_color))
                }else {
                    binding?.findContainer?.disabled()
                    binding?.find?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_unselected_color))
                }

                if(text.length == 5 )binding?.searchBar?.edit?.setSelection(text.length -1)
                else binding?.searchBar?.edit?.setSelection(text.length)
            }
            override fun onClear() {
                text = ""
                binding?.searchBar?.textHint = "0000 0000"
                binding?.findContainer?.dotColor =
                    ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.disable_btn)
                if(!isShowKeyboard) showKeyboard()
            }

        })
    }

    private fun hideKeyboard() {
        binding?.keyboard?.visibility = View.GONE
        binding?.findContainer?.visibility = View.GONE
        isShowKeyboard = false
    }

    private fun showKeyboard(){
        binding?.keyboard?.visibility = View.VISIBLE
        binding?.findContainer?.visibility = View.VISIBLE
        isShowKeyboard = true
        binding?.noFindContainer?.visibility = View.GONE
        binding?.petContainer?.visibility = View.GONE
    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.findPet.collect { findPet ->
                findPet?.let {
                    var pet = findPet.pet
                    hideKeyboard()
                    binding?.image?.loadImage(pet.photos.get(0).url)
                    var title = "${pet.name}, ${pet.bdate.let { context?.getMonthOnYear(it) }}"
                    val googleText = SpannableStringBuilder("$title *").apply {
                        setSpan(
                            ImageSpan(
                                requireContext(),
                                if(pet.sex == "MALE") com.petsvote.ui.R.drawable.ic_icon_sex_male
                                else com.petsvote.ui.R.drawable.ic_icon_sex_female,
                                ImageSpan.ALIGN_BASELINE
                            ), title.length + 1, title.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        );
                    }
                    binding?.name?.text = googleText
                    binding?.location?.text = "${pet.city_name}, ${pet.country_name}"
                    if(findPet.vote != null){
                        binding?.voteStatusTrue?.visibility = View.VISIBLE
                        binding?.voteContainer?.visibility = View.GONE
                        binding?.voteBar?.visibility = View.VISIBLE
                        binding?.voteBar?.isAnimUn = false
                        binding?.voteBar?.startVoteAnim(findPet.vote!! -1)
                        for(i in findPet.vote!! .. 4){
                            binding?.voteBar?.listStars?.get(i)?.visibility = View.GONE
                            binding?.voteBar?.listUnStars?.get(i)?.visibility = View.GONE
                        }

                    }else {
                        binding?.voteStatusTrue?.visibility = View.GONE
                        binding?.voteContainer?.visibility = View.VISIBLE
                        binding?.voteBar?.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when(it){
                    1 -> {
                        binding?.petContainer?.visibility = View.VISIBLE
                        binding?.containerRating?.visibility = View.VISIBLE
                    }
                    2 -> showStateNoFind()
                }
            }
        }

    }

    private fun showStateNoFind() {
        hideKeyboard()
        binding?.noFindContainer?.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectFindPet(this)
    }
}
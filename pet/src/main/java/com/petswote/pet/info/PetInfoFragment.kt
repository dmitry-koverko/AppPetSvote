package com.petswote.pet.info

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.getMonthOnYear
import com.petsvote.dialog.UserPhotoDialog
import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.pet.PetDetails
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.ext.sharePet
import com.petsvote.ui.ext.showSnackBar
import com.petsvote.ui.ext.spacingFormat
import com.petsvote.ui.loadImage
import com.petsvote.ui.openUrl
import com.petsvote.ui.parallax.HorizontalParallaxView
import com.petswote.pet.R
import com.petswote.pet.databinding.FragmentFindPetBinding
import com.petswote.pet.databinding.FragmentPetInfoBinding
import com.petswote.pet.di.PetComponentViewModel
import com.petswote.pet.find.FindPetViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class PetInfoFragment: BaseFragment(R.layout.fragment_pet_info),
    HorizontalParallaxView.HorizontalParallaxViewListener {

    @Inject
    internal lateinit var viewModelFactory: Lazy<PetInfoViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: PetInfoViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: FragmentPetInfoBinding? = null
    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    private var currentPet: Pet? = null
    private var currentPosition: Int = 0
    private var locate = ""
    private var kindString = ""

    companion object{
        fun newInstance(id: Int?, myPet: Boolean = false): PetInfoFragment{
            val args = Bundle()
            id?.let { args.putInt("pet", it) }
            myPet.let { args.putBoolean("myPet", it) }
            val fragment = PetInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPetInfoBinding.bind(view)
        var petId= arguments?.getInt("pet")
        petId?.let {
            viewModel.getPetInfo(it)
        }

        binding?.copy?.animation = true
        binding?.copy?.setOnClickListener {
            copyTextToClipboard()
        }

        binding?.close?.setOnClickListener {
            activity?.setResult(RESULT_OK)
            activity?.finish()
        }

        binding?.parallax?.mHorizontalParallaxViewListener = this

        if(arguments?.getBoolean("myPet") == true) {
            binding?.editContainer?.visibility = View.VISIBLE
            binding?.btnPromote?.visibility = View.VISIBLE
        }else binding?.userContainer?.visibility = View.VISIBLE

        binding?.editBl?.setOnClickListener {
            var bundle = Bundle()
            currentPet?.let { it1 -> bundle.putString("pet", Json.encodeToString(it1)) }
            bundle.putString("petKind", kindString)
            activity?.let { it1 -> navigation.startActivityEditPet(it1, bundle) }
        }

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiPet.collect { petData ->
                if (petData?.pet != null && petData.pet.id != -1) {
                    currentPet = petData.pet
                    var lString = mutableListOf<String>()
                    petData.pet.photos.let { list ->
                        for (i in list) {
                            lString.add(i.url)
                        }
                        if (list.isNotEmpty()) binding?.parallax?.list = lString
                    }
                    var title = "${petData.pet.name}, ${petData.pet.bdate.let { context?.getMonthOnYear(it) }}"
                    val googleText = SpannableStringBuilder("$title *").apply {
                        setSpan(
                            ImageSpan(
                                requireContext(),
                                if(petData.pet.sex == "MALE") com.petsvote.ui.R.drawable.ic_icon_sex_male_black
                                else com.petsvote.ui.R.drawable.ic_icon_sex_female_black,
                                ImageSpan.ALIGN_BASELINE
                            ), title.length + 1, title.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        );
                    }
                    binding?.petName?.text = googleText
                    //binding?.petLocate?.text = "${petData.pet.city_name}, ${petData.pet.country_name}"
                    binding?.city?.text = petData.pet.city_name
                    binding?.country?.text = petData.pet.country_name
                    petData.pet.pet_id.let { binding?.petId?.spacingFormat(it) }
                    if (petData.pet.inst.isNotEmpty()) {
                        binding?.instagramContainer?.visibility = View.VISIBLE
                        binding?.instagramContainer?.setOnClickListener {
                            openUrl("https://www.instagram.com/${petData.pet.inst}")
                        }
                    }

                    binding?.petShare?.setOnClickListener {
                        sharePet(petData.pet.pet_id)
                    }

                    locate = "${petData.pet.country_name}, ${petData.pet.city_name}"
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiPetDetails.collect { details ->
                if (details?.first_name?.isNotEmpty() == true) {
                    binding?.ratingBalls?.spacingFormat(details.global_score)
                    binding?.cityRating?.spacingFormat(
                        details.city_range,
                        getString(com.petsvote.ui.R.string.position)
                    )
                    binding?.countryRating?.spacingFormat(
                        details.country_range,
                        getString(com.petsvote.ui.R.string.position)
                    )
                    binding?.globalRating?.spacingFormat(
                        details.global_range,
                        getString(com.petsvote.ui.R.string.position)
                    )
                    binding?.ownerName?.text = "${details?.first_name} ${details.last_name}"
                    details.avatar.let { avatar ->
                        binding?.userImage?.loadImage(avatar)
                        binding?.userImage?.setOnClickListener {
                            UserPhotoDialog(avatar).show(childFragmentManager, "UserPhotoDialog")
                        }
                    }
                }

                lifecycleScope.launchWhenStarted {
                    viewModel.uiBreedString.collect {
                        binding?.petLocate?.text = "$it, $locate"
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiBreedString.collect {
                binding?.petLocate?.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiKindString.collect {
                kindString = it
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectPetInfo(this)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity?.setResult(RESULT_OK)
                activity?.finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    private fun copyTextToClipboard() {
        val textToCopy = binding?.petId?.text
        val clipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        binding?.petId?.let { showSnackBar(it, getString(com.petsvote.ui.R.string.text_copy_to_buffer)) }
    }

    override fun changePage(position: Int) {
        currentPosition = position
    }

    override fun onClick() {
        var bundle = Bundle()
        bundle.putString("pet", Json.encodeToString(currentPet))
        bundle.putInt("position", currentPosition)
        bundle.putString("location", binding?.petLocate?.text.toString())
        activity?.let { it1 -> navigation.startActivityPetPhotos(it1, bundle) }
    }
}
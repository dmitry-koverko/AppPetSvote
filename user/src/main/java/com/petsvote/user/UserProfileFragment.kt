package com.petsvote.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.dialog.SelectPhotoDialog
import com.petsvote.dialog.UserImageDialog
import com.petsvote.domain.entity.user.SaveUserParams
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.ext.disabled
import com.petsvote.ui.ext.enabled
import com.petsvote.ui.loadImage
import com.petsvote.user.R
import com.petsvote.user.crop.CropUserActivity
import com.petsvote.user.crop.CropUserImageFragment
import com.petsvote.user.databinding.FragmentUserProfileBinding
import com.petsvote.user.di.UserComponentViewModel
import com.petsvote.user.simple.SimpleUserViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile),
    SelectPhotoDialog.SelectPhotoDialogListener {

    @Inject
    internal lateinit var viewModelFactory: Lazy<UserProfileViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: UserProfileViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: FragmentUserProfileBinding? = null

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    private var isAddPhotoDialog = true;
    private val dialogAddPhoto = UserImageDialog()
    private val selectPhotoDialog = SelectPhotoDialog()

    var validateEditProfile = ValidateEditProfile()

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent?.getBooleanExtra(
                        CropUserImageFragment.EXTRA_MESSAGE_VALUE,
                        false
                    ) == true
                ) selectPhotoDialog.dismiss()
            }
        }

    companion object {
        const val EXTRA_MESSAGE = "CROP_MESSAGE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserProfileBinding.bind(view)

        initView()
        requestPermissions()

        lifecycleScope.launchWhenStarted { viewModel.getConfiguration() }
    }

    private fun initView() {

        dialogAddPhoto.setUserLocationDialogListener(object :
            UserImageDialog.UserImageDialogListener {
            override fun next() {
                viewModel.setAddPhotoDialog()
                isAddPhotoDialog = false
                showDialogAva()
            }

        })

        binding?.avatar?.setOnClickListener {
            showDialogAva()
        }

        binding?.selectCountry?.setOnClickListener {
            navigation.startSelectCountry()
        }

        binding?.selectCity?.setOnClickListener {
            navigation.startSelectCity()
        }

        binding?.username?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                validateEditProfile.name = p0.toString()
                checkBtn()
            }

        })

        binding?.save?.setOnClickListener {
            viewModel.saveUserInfo(SaveUserParams(
                binding?.username?.text?.toString() ?: "",
                binding?.lastname?.text?.toString() ?: ""
            ))
        }

    }

    private fun showDialogAva() {

        selectPhotoDialog.setSelectPhotoDialogListener(this)

        if (isAddPhotoDialog) {
            if (!dialogAddPhoto.isAdded)
                dialogAddPhoto.show(childFragmentManager, "UserImageDialog")
        } else {
            if (checkCameraPermissions() || checkReadPermissions()) {
                if (!selectPhotoDialog.isAdded)
                    selectPhotoDialog.show(childFragmentManager, "SelectPhotoDialog")
            }
        }
    }

    private fun checkBtn() {
        if (
            validateEditProfile.name.isNullOrEmpty() ||
            validateEditProfile.city == null ||
            validateEditProfile.country == null){
            binding?.save?.disabled()
            binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_unselected_color))
        }else{
            binding?.save?.enabled()
            binding?.saveText?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.besie_tab_text_selected_color))
        }
    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.isAddPhoto.collect {
                isAddPhotoDialog = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.imageCrop.collect {
                it?.let { bitmap ->
                    binding?.avatar?.loadImage(bitmap)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.imageUrl.collect { image ->
                image?.let { binding?.avatar?.loadImage(it) }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.country.collect { country ->
                if (country != null) {
                    binding?.country?.text = country
                    binding?.rightCity?.visibility = View.VISIBLE
                    binding?.selectCity?.isClickable = true
                } else {
                    binding?.rightCity?.visibility = View.GONE
                    binding?.selectCity?.isClickable = false
                }
                validateEditProfile.country = country
                checkBtn()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.city.collect { country ->
                if (country != null) {
                    binding?.city?.text = country
                }
                validateEditProfile.city = country
                checkBtn()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.firstName.collect {
                if (!it.isNullOrEmpty()) binding?.username?.setText(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.lastname.collect {
                if (!it.isNullOrEmpty()) binding?.lastname?.setText(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectUserProfile(this)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }


    override fun crop() {
        startForResult.launch(Intent(activity, CropUserActivity::class.java))
    }

    data class ValidateEditProfile(
        var name: String = "",
        var country: String? = null,
        var city: String? = null
    )
}
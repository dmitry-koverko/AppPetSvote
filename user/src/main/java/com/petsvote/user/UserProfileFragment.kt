package com.petsvote.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.dialog.SelectPhotoDialog
import com.petsvote.dialog.UserImageDialog
import com.petsvote.navigation.MainNavigation
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
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectUserProfile(this)
    }


    override fun crop() {
        startForResult.launch(Intent(activity, CropUserActivity::class.java))
    }
}
package com.petswote.pet.crop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petswote.pet.R
import com.petswote.pet.databinding.FragmentCropPetImageBinding
import com.petswote.pet.di.PetComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CropPetImageFragment: BaseFragment(R.layout.fragment_crop_pet_image) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<CropPetImageViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: CropPetImageViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentCropPetImageBinding? = null

    companion object {
        const val EXTRA_MESSAGE = "CROP_MESSAGE"
        const val EXTRA_MESSAGE_VALUE = "CROP_MESSAGE_VALUE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCropPetImageBinding.bind(view)

        binding?.crop?.setOnClickListener {
            val cropped: Bitmap? = binding?.cropViewImage?.getCroppedImage()
            cropped?.let { bitmap ->
                bitmapToArray(bitmap, ::setImage)
                var intent = Intent()
                intent.putExtra(EXTRA_MESSAGE_VALUE, true)
                activity?.setResult(Activity.RESULT_OK, intent)
                activity?.finish()
            }

        }
        binding?.cancel?.setOnClickListener {
            activity?.finish()
        }

        binding?.crop?.animation = true
        binding?.cancel?.animation = true


        lifecycleScope.launchWhenStarted { viewModel.getImage() }

    }

    fun setImage(byteArray: ByteArray){
        viewModel.setImageCrop(byteArray)
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.image.collect {
                it?.let {
                    binding?.cropViewImage?.setImageBitmap(it)
                    binding?.cropViewImage?.zoomTo(1.2f)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectCrop(this)
    }
}
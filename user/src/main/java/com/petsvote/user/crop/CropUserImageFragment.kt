package com.petsvote.user.crop

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.petsvote.core.BaseFragment
import com.petsvote.user.R
import com.petsvote.user.databinding.FragmentCropUserImageBinding
import com.petsvote.user.di.UserComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class CropUserImageFragment : BaseFragment(R.layout.fragment_crop_user_image) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<CropUserImageViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: CropUserImageViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentCropUserImageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCropUserImageBinding.bind(view)
        binding?.cropViewImage?.setCropperType(1)

        binding?.crop?.setOnClickListener {
            val cropped: Bitmap? = binding?.cropViewImage?.getCroppedImage()
            cropped?.let { bitmap ->
                viewModel.setImage()
                bitmapToArray(bitmap)
                findNavController().popBackStack()
            }

        }
        binding?.cancel?.setOnClickListener {
            viewModel.setImage()
            findNavController().popBackStack()
        }

        binding?.crop?.animation = true
        binding?.cancel?.animation = true


        lifecycleScope.launchWhenStarted { viewModel.getImage() }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.image.collect {
                it?.let {
                    binding?.cropViewImage?.setImageBitmap(it)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectCrop(this)
    }

    fun bitmapToArray(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imageInByte: ByteArray = stream.toByteArray()
        viewModel.setImageCrop(imageInByte)
    }
}
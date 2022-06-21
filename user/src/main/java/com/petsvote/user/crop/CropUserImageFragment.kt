package com.petsvote.user.crop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
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

    companion object {
        const val EXTRA_MESSAGE = "CROP_MESSAGE"
        const val EXTRA_MESSAGE_VALUE = "CROP_MESSAGE_VALUE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCropUserImageBinding.bind(view)
        binding?.cropViewImage?.setCropperType(1)

        binding?.crop?.setOnClickListener {
            val cropped: Bitmap? = binding?.cropViewImage?.getCroppedImage()
            cropped?.let { bitmap ->
                bitmapToArray(bitmap)
                var intent = Intent()
                intent.putExtra(EXTRA_MESSAGE_VALUE, true)
                activity?.setResult(Activity.RESULT_OK, intent)
                activity?.finish()
            }

        }
        binding?.cancel?.setOnClickListener {
            //viewModel.setImage()
            activity?.finish()
            //findNavController().popBackStack()
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
                    binding?.cropViewImage?.zoomTo(1.2f)
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
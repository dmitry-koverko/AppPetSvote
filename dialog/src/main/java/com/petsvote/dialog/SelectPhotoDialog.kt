package com.petsvote.dialog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.FragmentDialogSelectPhotoBinding
import com.petsvote.dialog.di.DialogComponentViewModel
import com.petsvote.dialog.enity.LocalPhoto
import com.petsvote.dialog.ui.AllPhotosAdapter
import com.petsvote.dialog.ui.CameraPreview
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.uriToBitmap
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_dialog_select_photo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.vponomarenko.injectionmanager.x.XInjectionManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// typeDialog 0 - userPhoto
// typeDialog 1 - petPhoto

class SelectPhotoDialog(val typeDialog: Int = 0): BaseDialog(R.layout.fragment_dialog_select_photo),
    AllPhotosAdapter.OnSelectedItem {

    private val TAG = SelectPhotoDialog::class.java.name

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectPhotoViewModel.Factory>

    private val dialogComponentViewModel: DialogComponentViewModel by viewModels()
    private val viewModel: SelectPhotoViewModel by viewModels {
        viewModelFactory.get()
    }

    private val REQUEST_ID = 123
    private val PICK_PHOTO_CODE = 1046
    private val CROP_REQUEST = 104

    lateinit var currentPhotoPath: String
    lateinit var photoURIReq: Uri

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    private val mPicture: android.hardware.Camera.PictureCallback? = null
    private lateinit var  cameraProvider: ProcessCameraProvider
    private lateinit var binding: FragmentDialogSelectPhotoBinding

    private var listPhoto = mutableListOf<LocalPhoto>()
    private var photoAdapter = AllPhotosAdapter(listPhoto)

    var mDialogListener: SelectPhotoDialogListener? = null

    companion object {
        const val EXTRA_MESSAGE = "CROP_MESSAGE"
        const val EXTRA_MESSAGE_VALUE = "CROP_MESSAGE_VALUE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDialogSelectPhotoBinding.bind(view)

        lifecycleScope.launchWhenStarted { viewModel.dismiss.value = false}

        lifecycleScope.launchWhenStarted {
            viewModel.localPhotos.collect {list ->

                if(list.isNotEmpty()) photoAdapter.submit(list.takeLast(20))
            }
        }

        lifecycleScope.launchWhenStarted {
//            viewModel.dismiss.collect {
//                if(it) dismiss()
//            }
        }

        var mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.photosList.apply {
            layoutManager = mLayoutManager
            this.adapter = photoAdapter
        }

        photoAdapter.setOnSelected(this)

        binding.cancel.setOnClickListener { dismiss() }
        binding.allPhotos.setOnClickListener { if(checkReadPermissions()) pickPhoto() }
        binding.cardPreview.setOnClickListener { if(checkCameraPermissions()) launchCameraRawPhoto() }


        object : CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                binding.selectProgress.visibility = View.GONE
                if(checkCameraPermissions()) startCamera()
                if(checkReadPermissions()) getLocalImages()
            }

        }.start()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun launchCameraRawPhoto() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            requestPermissions()
            null
        }

        photoFile?.let {
            photoURIReq = FileProvider.getUriForFile(
                requireContext(),
                "com.example.android.fileprovider",
                photoFile);
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURIReq);
            startActivityForResult(cameraIntent, REQUEST_ID)
        }

    }

    fun getLocalImages(){
        val contentResolver =
            requireContext().contentResolver
        var cursor = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        lifecycleScope.launchWhenStarted { viewModel.getLocalImages(cursor, contentResolver) }
    }

    private fun startCamera() {
        binding.cardPreview.visibility = View.VISIBLE
        val cameraProviderFuture = context?.let { ProcessCameraProvider.getInstance(it) }

        cameraProviderFuture?.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                    }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()), ImageAnalysis.Analyzer { imageProxy ->
                   if(viewFinder != null){
                       var bm: Bitmap? = viewFinder.bitmap
                       binding.imgPreview.setImageBitmap(bm)
                       imageProxy.close()
                   }
                })

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageAnalysis, preview)

            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }

        }, context?.let { ContextCompat.getMainExecutor(it) })
    }

    private fun pickPhoto(){
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        if (context?.getPackageManager()?.let { intent.resolveActivity(it) } != null) {
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val contentResolver =
            requireContext().contentResolver
        if (resultCode === Activity.RESULT_OK && requestCode == REQUEST_ID) {
            val contentResolver =
                requireContext().contentResolver
            var bt= uriToBitmap(Uri.fromFile( File(currentPhotoPath)), 1.2f, contentResolver)
            bt?.let { bitmapToBytes(it) }

        }
            else if (resultCode === Activity.RESULT_OK && data != null && requestCode == PICK_PHOTO_CODE) {
            val photoUri: Uri? = data.data
            var bt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                photoUri?.let {
                    ImageDecoder.createSource(requireContext().contentResolver,
                        it
                    )
                }?.let { ImageDecoder.decodeBitmap(it) }
            } else {
                null
            }
            bt?.let { bitmapToBytes(it) }

        }
    }

    override fun select(photo: LocalPhoto) {
        var bt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            photo.bitmap?.let {
                ImageDecoder.createSource(requireContext().contentResolver,
                    it
                )
            }?.let { ImageDecoder.decodeBitmap(it) }
        } else {
            null
        }
        bt?.let { bitmapToBytes(it) }
    }

    interface SelectPhotoDialogListener{
        fun crop()
    }

    fun setSelectPhotoDialogListener(listener: SelectPhotoDialogListener){
        mDialogListener = listener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogComponentViewModel.dialogComponent.inject(this)
    }

    fun bitmapToBytes(bitmap: Bitmap){
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imageInByte: ByteArray = stream.toByteArray()
        if(typeDialog == 0) lifecycleScope.launch (Dispatchers.IO){ viewModel.setImage(imageInByte) }
        else lifecycleScope.launch (Dispatchers.IO){ viewModel.setImagePet(imageInByte) }
        mDialogListener?.crop()
    }

    /*
    holder.contact_number.text= listContact?.get(position)!!.contact_number;
        val imageStream = ByteArrayInputStream(listContact?.get(position)!!.image)
        val theImage = BitmapFactory.decodeStream(imageStream)
        holder.img.setImageBitmap(theImage)
     */
}
package com.petswote.pet.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petsvote.dialog.SelectPhotoDialog
import com.petsvote.dialog.SelectPhotoDialog.Companion.EXTRA_MESSAGE_VALUE
import com.petsvote.dialog.UserImageDialog
import com.petsvote.domain.entity.pet.PetPhoto
import com.petswote.pet.R
import com.petswote.pet.crop.CropPetActivity
import com.petswote.pet.databinding.FragmentAddPetBinding
import com.petswote.pet.di.PetComponentViewModel
import com.petswote.pet.helpers.OnStartDragListener
import com.petswote.pet.helpers.SimpleItemTouchHelperCallback
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AddPetFragment: BaseFragment(R.layout.fragment_add_pet), OnStartDragListener,
    SelectPhotoDialog.SelectPhotoDialogListener {

    private var mItemTouchHelper: ItemTouchHelper? = null
    private var listPhotos = mutableListOf<PetPhoto>()
    private val adapter = AddPetPhotoAdapter(activity, this)

    private var binding: FragmentAddPetBinding? = null

    private var isAddPhotoDialog = true;
    private val dialogAddPhoto = UserImageDialog()
    private val selectPhotoDialog = SelectPhotoDialog(1)

    @Inject
    internal lateinit var viewModelFactory: Lazy<AddPetViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: AddPetViewModel by viewModels {
        viewModelFactory.get()
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent?.getBooleanExtra(
                        EXTRA_MESSAGE_VALUE,
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

        binding = FragmentAddPetBinding.bind(view)

        initRecycler()
        initDialog()
        requestPermissions()

        lifecycleScope.launchWhenStarted { viewModel.getConfiguration() }
    }

    private fun initDialog() {
        dialogAddPhoto.setUserLocationDialogListener(object :
            UserImageDialog.UserImageDialogListener {
            override fun next() {
                //viewModel.setAddPhotoDialog()
                isAddPhotoDialog = false
                showDialogImage()
            }

        })
    }

    private fun initRecycler() {

        val recyclerView: RecyclerView? = binding?.photosPetList
        val layoutManager = GridLayoutManager(activity, 3)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)

        if (listPhotos.isEmpty()) {
            for (i in 0..5) {
                listPhotos.add(PetPhoto(null))
            }
            adapter.addData(listPhotos)
        }

        recyclerView?.setHasFixedSize(true)
        recyclerView?.setAdapter(adapter)
        recyclerView?.setLayoutManager(layoutManager)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(recyclerView)
    }


    private fun showDialogImage() {

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
            viewModel.listPhotosPet.collect {list ->
                for (i in list)
                    adapter.addItem(i)
            }
        }

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {

    }

    override fun onClick() {
        showDialogImage()
    }

    override fun onClose(petPhoto: PetPhoto) {
        viewModel.removeItem(petPhoto.id ?: -1)
    }

    override fun crop() {
        startForResult.launch(Intent(activity, CropPetActivity::class.java))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.inject(this)

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
}
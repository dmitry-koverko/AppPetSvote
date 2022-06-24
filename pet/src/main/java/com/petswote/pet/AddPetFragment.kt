package com.petswote.pet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.BaseFragment
import com.petswote.pet.databinding.FragmentAddPetBinding
import com.petswote.pet.helpers.OnStartDragListener
import com.petswote.pet.helpers.PetPhoto
import com.petswote.pet.helpers.SimpleItemTouchHelperCallback

class AddPetFragment: BaseFragment(R.layout.fragment_add_pet), OnStartDragListener {

    private var mItemTouchHelper: ItemTouchHelper? = null
    private var listPhotos = mutableListOf<PetPhoto>()
    private val adapter = AddPetPhotoAdapter(activity, this)
    private var isAddPhoto = false

    private var binding: FragmentAddPetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPetBinding.bind(view)

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

    override fun initObservers() {

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {

    }

    override fun onClick() {

    }

    override fun onClose(position: Int) {

    }
}
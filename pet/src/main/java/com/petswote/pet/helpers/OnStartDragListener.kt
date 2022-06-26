package com.petswote.pet.helpers

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.domain.entity.pet.PetPhoto

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    fun onClick()
    fun onClose(petPhoto: PetPhoto)
}
package com.petsvote.dialog

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.DialogComplaintSuccessBinding
import com.petsvote.ui.loadImageBlure

class ComplaintSuccessDialog(private val bgBitmap: Bitmap) :
    BaseDialog(R.layout.dialog_complaint_success, true, false) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = DialogComplaintSuccessBinding.bind(view)
        binding.bg.loadImageBlure(bgBitmap)
    }
}
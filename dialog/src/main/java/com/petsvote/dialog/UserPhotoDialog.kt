package com.petsvote.dialog

import android.os.Bundle
import android.view.View
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.DialogUserPhotoBinding
import com.petsvote.ui.loadImage

class UserPhotoDialog(private var userImage: String): BaseDialog(com.petsvote.dialog.R.layout.dialog_user_photo, true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var width = resources.displayMetrics.widthPixels - resources.displayMetrics.density * 32

        var binding = DialogUserPhotoBinding.bind(view)

        var lp = binding.userImage.layoutParams
        lp.width = width.toInt()
        lp.height = width.toInt()
        binding.userImage.layoutParams = lp
        binding.userImage.loadImage(userImage)

        binding.root.setOnClickListener { dismiss() }
    }

}
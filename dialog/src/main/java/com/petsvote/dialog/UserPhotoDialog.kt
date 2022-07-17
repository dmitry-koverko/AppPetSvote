package com.petsvote.dialog

import android.os.Bundle
import android.view.View
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.DialogUserPhotoBinding
import com.petsvote.ui.loadImage

class UserPhotoDialog(private var userImage: String): BaseDialog(com.petsvote.dialog.R.layout.dialog_user_photo, true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = DialogUserPhotoBinding.bind(view)
        binding.userImage.loadImage(userImage)
    }

}
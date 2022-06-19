package com.petsvote.dialog

import android.os.Bundle
import android.view.View
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.FragmentDialogUserImageBinding
import com.petsvote.dialog.databinding.FragmentDialogUserLocationBinding

class UserImageDialog: BaseDialog(R.layout.fragment_dialog_user_image) {

    private var mUserPhotoDialogListener: UserImageDialogListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmentDialogUserImageBinding.bind(view)


        binding.ok.setOnClickListener {
            it.isPressed = true
            mUserPhotoDialogListener?.next()
            dismiss()
        }
    }


    fun setUserLocationDialogListener(listener: UserImageDialogListener){
        mUserPhotoDialogListener = listener
    }

    interface UserImageDialogListener{
        fun next()
    }

    companion object{
        val TAG = UserLocationDialog::class.java.name
    }

}
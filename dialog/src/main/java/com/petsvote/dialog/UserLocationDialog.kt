package com.petsvote.dialog

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.FragmentDialogUserLocationBinding

class UserLocationDialog: BaseDialog(R.layout.fragment_dialog_user_location) {

    private var mUserLocationDialogListener: UserLocationDialogListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmentDialogUserLocationBinding.bind(view)

        binding.containerCancel.setOnClickListener {
            it.isPressed = true
            dismiss()
        }

        binding.containerNext.setOnClickListener {
            it.isPressed = true
            mUserLocationDialogListener?.next()
            dismiss()
        }
    }


    fun setUserLocationDialogListener(listener: UserLocationDialogListener){
        mUserLocationDialogListener = listener
    }

    interface UserLocationDialogListener{
        fun next()
    }

    companion object{
        val TAG = UserLocationDialog::class.java.name
    }

}
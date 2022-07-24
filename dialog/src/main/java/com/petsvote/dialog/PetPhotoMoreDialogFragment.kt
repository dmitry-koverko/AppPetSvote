package com.petsvote.dialog

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.DialogPetPhotoMoreBinding

class PetPhotoMoreDialogFragment(): BaseDialog(R.layout.dialog_pet_photo_more, true) {

    var mPetPhotoMoreDialogFragmentListener: PetPhotoMoreDialogFragmentListener? = null
    private lateinit var binding: DialogPetPhotoMoreBinding
    val TAG: String = PetPhotoMoreDialogFragment::class.java.name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogPetPhotoMoreBinding.bind(view)

        binding.complaint.setOnClickListener {
            mPetPhotoMoreDialogFragmentListener?.complaint()
            dismiss()
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        //startAnimtoTop()
    }

    private fun startAnimtoTop() {
        val propertyXLeft: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat("TRANSITIONY", 500f, 0f)

        var animator = ValueAnimator()
        animator!!.setValues(propertyXLeft)
        animator!!.setDuration(200)
        animator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            var pointY = animation.getAnimatedValue("TRANSITIONY") as Float
            binding.card.translationY = pointY
        })
        animator!!.start()
    }

    interface PetPhotoMoreDialogFragmentListener {
        fun complaint()
    }
}
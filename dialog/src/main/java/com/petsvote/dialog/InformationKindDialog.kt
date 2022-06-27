package com.petsvote.dialog

import android.os.Bundle
import android.view.View
import com.petsvote.core.BaseDialog
import com.petsvote.dialog.databinding.DialogInformationKindBinding

class InformationKindDialog: BaseDialog(R.layout.dialog_information_kind) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = DialogInformationKindBinding.bind(view)
        binding.ok.setOnClickListener {
            dismiss()
        }

    }

}
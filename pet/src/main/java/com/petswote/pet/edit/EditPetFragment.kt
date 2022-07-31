package com.petswote.pet.edit

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.ext.parseDateString
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.pet.PetPhoto
import com.petswote.pet.add.AddPetFragment
import com.petswote.pet.databinding.FragmentAddPetBinding
import com.petswote.pet.info.PetInfoFragment
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EditPetFragment: AddPetFragment() {

   private var binding: FragmentAddPetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPetBinding.bind(view)

        binding?.title?.text = getString(com.petsvote.ui.R.string.pet_edit)

        var pet: Pet? = Json.decodeFromString(activity?.intent?.extras?.getString("pet") ?: "") as Pet
        pet?.let {
            binding?.username?.setText(it.name, TextView.BufferType.EDITABLE)

            binding?.birthday?.text = parseDateString(it.bdate)
            binding?.birthday?.alpha = 1f
            binding?.birthday?.setTextColor(ContextCompat.getColor(requireContext(), com.petsvote.ui.R.color.active_text_color))
            validateCreatePet.birthday = pet.bdate

            when (pet.type) {
                "reptile", "amphibian", "invertebrates" -> {
                    binding?.containerTabs2?.visibility = View.VISIBLE
                    binding?.containerTabs?.visibility = View.GONE
                }
                else -> {
                    binding?.containerTabs2?.visibility = View.GONE
                    binding?.containerTabs?.visibility = View.VISIBLE
                }
            }

            for (i in 0 until pet.photos.size)
                adapter.addItem(PetPhoto(id = i, image = pet.photos[i].url))

            viewModel.getKindByName(pet.type)
            viewModel.setBreedsByIdName(pet.breed_id)

            if(pet.inst.isNotEmpty()) stateInstagramEnabled(it.inst)
        }

        binding?.containerKids?.setOnClickListener {
            navigation.startSelectKindsFromEditPet()
        }

        binding?.containerBreeds?.setOnClickListener {
            if(isBreedClick){
                navigation.startSelectBreedsFromEditPet()
            }
            else if (!informationDialog.isAdded)
                informationDialog.show(childFragmentManager, "informationDialog")
        }

    }

    override fun initObservers() {
        super.initObservers()
    }

}
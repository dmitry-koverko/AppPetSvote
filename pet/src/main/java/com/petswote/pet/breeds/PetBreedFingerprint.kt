package com.petswote.pet.breeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.breed.Breed
import com.petswote.pet.R
import com.petswote.pet.databinding.ItemPetBreedsBinding

class PetBreedFingerprint(
    private val onSelectBreed: (Breed) -> Unit
) : ItemFingerprint<ItemPetBreedsBinding, Breed> {

    override fun isRelativeItem(item: Item) = item is Breed

    override fun getLayoutId() = R.layout.item_pet_breeds

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemPetBreedsBinding, Breed> {
        val binding = ItemPetBreedsBinding.inflate(layoutInflater, parent, false)
        return ItemBreedViewHolder(binding, onSelectBreed)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Breed>() {

        override fun areItemsTheSame(oldItem: Breed, newItem: Breed) =
            oldItem.breedId == newItem.breedId

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed) = oldItem.isSelect == newItem.isSelect

    }

}

class ItemBreedViewHolder(
    binding: ItemPetBreedsBinding,
    val onSelectBreed: (Breed) -> Unit
) : BaseViewHolder<ItemPetBreedsBinding, Breed>(binding) {

    override fun onBind(item: Breed) {
        super.onBind(item)
        with(binding) {
            binding.title.text = item.breedName
            binding.check.visibility = if(item.isSelect) android.view.View.VISIBLE else android.view.View.GONE
            binding.root.setOnClickListener {
                onSelectBreed(item)
            }
        }
    }

}
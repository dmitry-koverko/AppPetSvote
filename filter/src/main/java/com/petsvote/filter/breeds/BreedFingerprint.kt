package com.petsvote.filter.breeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.filter.R
import com.petsvote.filter.databinding.ItemBreedsBinding

class BreedFingerprint(
    private val onSelectBreed: (Breed) -> Unit
) : ItemFingerprint<ItemBreedsBinding, Breed> {

    override fun isRelativeItem(item: Item) = item is Breed

    override fun getLayoutId() = R.layout.item_breeds

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemBreedsBinding, Breed> {
        val binding = ItemBreedsBinding.inflate(layoutInflater, parent, false)
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
    binding: ItemBreedsBinding,
    val onSelectBreed: (Breed) -> Unit
) : BaseViewHolder<ItemBreedsBinding, Breed>(binding) {

    override fun onBind(item: Breed) {
        super.onBind(item)
        with(binding) {
            binding.title.text = item.breedName
            binding.check.visibility = if(item.isSelect) View.VISIBLE else View.GONE
            binding.root.setOnClickListener {
                onSelectBreed(item)
            }
            if(item.breedName.contains(binding.root.context.getString(com.petsvote.ui.R.string.no_breed))) binding.dividerBreed.visibility = View.VISIBLE
            else binding.dividerBreed.visibility = View.GONE
        }
    }

}
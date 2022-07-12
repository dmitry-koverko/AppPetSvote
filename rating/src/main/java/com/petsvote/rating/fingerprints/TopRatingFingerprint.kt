package com.petsvote.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.rating.databinding.ItemRatingBinding
import com.petsvote.rating.databinding.ItemTopBinding

class TopRatingFingerprint(
    private val onClick: (RatingPet) -> Unit
) : ItemFingerprint<ItemRatingBinding, RatingPet> {

    override fun isRelativeItem(item: Item) = item is RatingPet

    override fun getLayoutId() = R.layout.item_rating

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemRatingBinding, RatingPet> {
        val binding = ItemRatingBinding.inflate(layoutInflater, parent, false)
        return TopRatingViewHolder(binding, onClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<RatingPet>() {

        override fun areItemsTheSame(oldItem: RatingPet, newItem: RatingPet) =
            oldItem.pet_id == newItem.pet_id

        override fun areContentsTheSame(oldItem: RatingPet, newItem: RatingPet) = oldItem == newItem

    }

}

class TopRatingViewHolder(
    binding: ItemRatingBinding,
    private val onClick: (RatingPet) -> Unit
) : BaseViewHolder<ItemRatingBinding, RatingPet>(binding) {


    override fun onBind(item: RatingPet) {
        super.onBind(item)
        with(binding) {
            when(item.itemType){
                RatingPetItemType.DEFAULT -> {
                    binding.root.setDefaultLP()
                    binding.root.setMask(item.isUserPet)
                    if (item.photos.isNotEmpty())
                        binding.root.setImageCat(item.photos.first().url)
                    binding.root.setName(item.name)
                    binding.root.setLocation(
                        if (item.locationType == RatingFilterLocationType.WORLD)
                            "${item.city_name}, ${item.country_name}"
                        else if(item.locationType == RatingFilterLocationType.COUNTRY) item.city_name
                        else ""
                    )
                    binding.root.setPosition(item.index)
                    binding.root.setCorona()
                }
                RatingPetItemType.TOP -> {
                    binding.root.setTopLP()
                    binding.root.setMask(item.isUserPet)
                    if (item.photos.isNotEmpty())
                        binding.root.setImageCat(item.photos.first().url)
                    binding.root.setName(item.name)
                    binding.root.setLocation(
                        if (item.locationType == RatingFilterLocationType.WORLD)
                            "${item.city_name}, ${item.country_name}"
                        else if(item.locationType == RatingFilterLocationType.COUNTRY) item.city_name
                        else ""
                    )
                    binding.root.setPosition(item.index)
                    binding.root.setCorona(
                        item.locationType == RatingFilterLocationType.WORLD
                    )
                }
                RatingPetItemType.ADDPET -> binding.root.setAppPetLP()
                RatingPetItemType.TOPADDPET -> binding.root.setTopAppPetLP()
                RatingPetItemType.NULLABLE -> binding.root.setNullableLP()
            }

            binding.root.setOnClickListener { onClick(item) }
//            if (item.itemType == RatingPetItemType.DEFAULT || item.itemType == RatingPetItemType.TOP) {
//                binding.root.setMask(item.isUserPet)
//                if (item.photos.isNotEmpty())
//                    binding.root.setImageCat(item.photos.first().url)
//                binding.root.setName(item.name)
//                binding.root.setLocation(
//                    if (item.locationType == RatingFilterLocationType.WORLD)
//                        "${item.city_name}, ${item.country_name}"
//                    else item.city_name
//                )
//            }else binding.root.removeAllViews()

        }
    }

}
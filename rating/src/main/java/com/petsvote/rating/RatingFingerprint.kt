package com.petsvote.rating
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.rating.databinding.ItemRatingBinding
import com.petsvote.rating.databinding.ItemTopBinding

class RatingFingerprint : ItemFingerprint<ItemRatingBinding, RatingPet> {

    override fun isRelativeItem(item: Item) = item is RatingPet

    override fun getLayoutId() = R.layout.item_rating

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemRatingBinding, RatingPet> {
        val binding = ItemRatingBinding.inflate(layoutInflater, parent, false)
        return RatingViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<RatingPet>() {

        override fun areItemsTheSame(oldItem: RatingPet, newItem: RatingPet) = oldItem.pet_id == newItem.pet_id

        override fun areContentsTheSame(oldItem: RatingPet, newItem: RatingPet) = oldItem == newItem

    }

}

class RatingViewHolder(
    binding: ItemRatingBinding
) : BaseViewHolder<ItemRatingBinding, RatingPet>(binding) {


    override fun onBind(item: RatingPet) {
        super.onBind(item)
        with(binding) {
        }
    }

    override fun onBind(item: RatingPet, payloads: List<Any>) {
        super.onBind(item, payloads)
    }

}

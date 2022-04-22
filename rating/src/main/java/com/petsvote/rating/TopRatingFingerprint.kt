package com.petsvote.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.rating.databinding.ItemTopBinding

class ItemTopRatingFingerprint : ItemFingerprint<ItemTopBinding, RatingPet> {

    override fun isRelativeItem(item: Item) = item is RatingPet

    override fun getLayoutId() = R.layout.item_top

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemTopBinding, RatingPet> {
        val binding = ItemTopBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<RatingPet>() {

        override fun areItemsTheSame(oldItem: RatingPet, newItem: RatingPet) = oldItem.pet_id == newItem.pet_id

        override fun areContentsTheSame(oldItem: RatingPet, newItem: RatingPet) = oldItem == newItem

    }

}

class PostViewHolder(
    binding: ItemTopBinding
) : BaseViewHolder<ItemTopBinding, RatingPet>(binding) {


    override fun onBind(item: RatingPet) {
        super.onBind(item)
        with(binding) {
//            tvCommentCount.text = item.commentsCount
//            tvLikesCount.text = item.likesCount
//            tvTitle.text = item.mainComment
//            ivPostImage.setImageDrawable(item.image)
//            tbLike.setChecked(item.isSaved)
        }
    }

    override fun onBind(item: RatingPet, payloads: List<Any>) {
        super.onBind(item, payloads)
        //binding.tbLike.setChecked(isSaved)
    }

    private fun ImageView.setMask(isUserPet: Boolean) {
        val icon = when (isUserPet) {
            true -> com.petsvote.ui.R.drawable.mask_user
            false -> com.petsvote.ui.R.drawable.mask_default
        }
        setImageResource(icon)
    }

}
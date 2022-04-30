package com.petsvote.rating.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.rating.R
import com.petsvote.rating.databinding.ItemFindBinding

class FindPetFingerprint(
    private val onClickFind: (SimpleItem) -> Unit
) : ItemFingerprint<ItemFindBinding, SimpleItem> {

    override fun isRelativeItem(item: Item) = item is SimpleItem

    override fun getLayoutId() = R.layout.item_find

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemFindBinding, SimpleItem> {
        val binding = ItemFindBinding.inflate(layoutInflater, parent, false)
        return ItemFindViewHolder(binding, onClickFind)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<SimpleItem>() {

        override fun areItemsTheSame(oldItem: SimpleItem, newItem: SimpleItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SimpleItem, newItem: SimpleItem) = oldItem == newItem

    }

}

class ItemFindViewHolder(
    binding: ItemFindBinding,
    val onClickFind: (SimpleItem) -> Unit
) : BaseViewHolder<ItemFindBinding, SimpleItem>(binding) {

    override fun onBind(item: SimpleItem) {
        super.onBind(item)
        with(binding) {
            binding.blFind.setOnClickListener {
                onClickFind(item)
            }
        }
    }

    override fun onBind(item: SimpleItem, payloads: List<Any>) {
        super.onBind(item, payloads)
    }

}
package com.petsvote.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.filter.databinding.ItemKindsBinding

class KindsFingerprint(
    private val onSelectKind: (Kind) -> Unit
) : ItemFingerprint<ItemKindsBinding, Kind> {

    override fun isRelativeItem(item: Item) = item is Kind

    override fun getLayoutId() = R.layout.item_kinds

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemKindsBinding, Kind> {
        val binding = ItemKindsBinding.inflate(layoutInflater, parent, false)
        return ItemKindViewHolder(binding, onSelectKind)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Kind>() {

        override fun areItemsTheSame(oldItem: Kind, newItem: Kind) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Kind, newItem: Kind) = oldItem == newItem

    }

}

class ItemKindViewHolder(
    binding: ItemKindsBinding,
    val onSelectKind: (Kind) -> Unit
) : BaseViewHolder<ItemKindsBinding, Kind>(binding) {

    override fun onBind(item: Kind) {
        super.onBind(item)
        with(binding) {
            binding.title.text = item.title
        }
    }

}
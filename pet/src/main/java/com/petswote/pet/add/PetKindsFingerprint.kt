package com.petswote.pet.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.filter.Kind
import com.petswote.pet.R
import com.petswote.pet.databinding.ItemKindsPetBinding

class PetKindsFingerprint(
    private val onSelectKind: (Kind) -> Unit
) : ItemFingerprint<ItemKindsPetBinding, Kind> {

    override fun isRelativeItem(item: Item) = item is Kind

    override fun getLayoutId() = R.layout.item_kinds_pet

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemKindsPetBinding, Kind> {
        val binding = ItemKindsPetBinding.inflate(layoutInflater, parent, false)
        return ItemKindsPetHolder(binding, onSelectKind)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Kind>() {

        override fun areItemsTheSame(oldItem: Kind, newItem: Kind) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Kind, newItem: Kind) = oldItem.id == newItem.id

    }

}

class ItemKindsPetHolder(
    binding: ItemKindsPetBinding,
    val onSelectKind: (Kind) -> Unit
) : BaseViewHolder<ItemKindsPetBinding, Kind>(binding) {

    override fun onBind(item: Kind) {
        super.onBind(item)
        with(binding) {
            binding.title.text = item.title
            binding.check.visibility = if(item.isSelect) View.VISIBLE else View.GONE
            binding.root.setOnClickListener {
                onSelectKind(item)
            }
        }
    }

}
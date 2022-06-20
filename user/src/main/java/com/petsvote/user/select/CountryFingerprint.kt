package com.petsvote.user.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.user.R
import com.petsvote.user.databinding.ItemCountryBinding

class CountryFingerprint(
    private val onSelectCountry: (Country) -> Unit
) : ItemFingerprint<ItemCountryBinding, Country> {

    override fun isRelativeItem(item: Item) = item is Country

    override fun getLayoutId() = R.layout.item_country

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCountryBinding, Country> {
        val binding = ItemCountryBinding.inflate(layoutInflater, parent, false)
        return ItemCountryViewHolder(binding, onSelectCountry)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Country>() {

        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Country, newItem: Country) = oldItem.isSelect == newItem.isSelect

    }

}

class ItemCountryViewHolder(
    binding: ItemCountryBinding,
    val onSelectCountry: (Country) -> Unit
) : BaseViewHolder<ItemCountryBinding, Country>(binding) {

    override fun onBind(item: Country) {
        super.onBind(item)
        with(binding) {
            binding.title.text = item.title
            binding.check.visibility = if(item.isSelect) android.view.View.VISIBLE else android.view.View.GONE
            binding.root.setOnClickListener {
                onSelectCountry(item)
            }

        }
    }

}
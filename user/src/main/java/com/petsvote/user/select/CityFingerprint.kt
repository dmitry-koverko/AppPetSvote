package com.petsvote.user.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.user.R
import com.petsvote.user.databinding.ItemCityBinding
import com.petsvote.user.databinding.ItemCountryBinding

class CityFingerprint(
    private val onSelectCity: (City) -> Unit
) : ItemFingerprint<ItemCityBinding, City> {

    override fun isRelativeItem(item: Item) = item is City

    override fun getLayoutId() = R.layout.item_city

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCityBinding, City> {
        val binding = ItemCityBinding.inflate(layoutInflater, parent, false)
        return ItemCityViewHolder(binding, onSelectCity)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(oldItem: City, newItem: City) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem.isSelect == newItem.isSelect

    }

}

class ItemCityViewHolder(
    binding: ItemCityBinding,
    val onSelectCity: (City) -> Unit
) : BaseViewHolder<ItemCityBinding, City>(binding) {

    override fun onBind(item: City) {
        super.onBind(item)
        with(binding) {

            binding.titile.text = item.title
            binding.check.visibility = if(item.isSelect)  View.VISIBLE else View.GONE
            if(item.region?.isNotEmpty() == true){
                binding.subText.visibility = View.VISIBLE
                binding.subText.text = item.region
            }

            binding.root.setOnClickListener {
                binding.root.isPressed = true
                onSelectCity(item)
            }

        }
    }

}
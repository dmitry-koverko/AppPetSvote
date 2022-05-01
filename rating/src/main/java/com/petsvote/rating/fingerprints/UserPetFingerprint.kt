package com.petsvote.rating.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.rating.R
import com.petsvote.rating.databinding.ItemFindBinding
import com.petsvote.rating.databinding.ItemUserPetBinding

class UserPetFingerprint(
private val onClickFind: (UserPet) -> Unit
) : ItemFingerprint<ItemUserPetBinding, UserPet> {

    override fun isRelativeItem(item: Item) = item is UserPet

    override fun getLayoutId() = R.layout.item_user_pet

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemUserPetBinding, UserPet> {
        val binding = ItemUserPetBinding.inflate(layoutInflater, parent, false)
        return UserPetViewHolder(binding, onClickFind)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UserPet>() {

        override fun areItemsTheSame(oldItem: UserPet, newItem: UserPet) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserPet, newItem: UserPet) = oldItem == newItem

    }

}

class UserPetViewHolder(
    binding: ItemUserPetBinding,
    val onClickFind: (UserPet) -> Unit
) : BaseViewHolder<ItemUserPetBinding, UserPet>(binding) {

    override fun onBind(item: UserPet) {
        super.onBind(item)
        with(binding) {

        }
    }

    override fun onBind(item: UserPet, payloads: List<Any>) {
        super.onBind(item, payloads)
    }

}
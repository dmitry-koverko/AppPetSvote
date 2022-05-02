package com.petsvote.rating.fingerprints

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.domain.entity.pet.SimpleItem
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.rating.R
import com.petsvote.rating.databinding.ItemFindBinding
import com.petsvote.rating.databinding.ItemUserPetBinding
import com.petsvote.ui.dpToPx
import com.petsvote.ui.loadImage

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

        override fun areContentsTheSame(oldItem: UserPet, newItem: UserPet) =
            oldItem.isClickPet == newItem.isClickPet

        override fun getChangePayload(oldItem: UserPet, newItem: UserPet): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

    }
}

class UserPetViewHolder(
    binding: ItemUserPetBinding,
    val onClickFind: (UserPet) -> Unit
) : BaseViewHolder<ItemUserPetBinding, UserPet>(binding) {

    override fun onBind(item: UserPet) {
        super.onBind(item)
        with(binding) {
            binding.petImageSmall.setOnClickListener {
                onClickFind(item)
            }
            val heightSmall = dpToPx(40f, binding.root.context)
            val heightBig = dpToPx(50f, binding.root.context)
            val defaultMarginLeft  = dpToPx(66f, binding.root.context)
            val smallMarginLeft  = dpToPx(56f, binding.root.context)

            if(item.position == 0 && item.isClickPet){
                val lp = FrameLayout.LayoutParams(heightBig, heightBig)
                lp.gravity = Gravity.CENTER
                binding.petImageSmall.layoutParams = lp
                val lpContainer = binding.userPetContainer.layoutParams
                lpContainer.width = heightBig
                binding.userPetContainer.layoutParams = lpContainer
            }
            else if (item.position == 0 && !item.isClickPet) {
                val lp = FrameLayout.LayoutParams(heightSmall, heightSmall)
                lp.gravity = Gravity.BOTTOM
                binding.petImageSmall.layoutParams = lp
                val lpContainer = binding.userPetContainer.layoutParams
                lpContainer.width = heightSmall
                binding.userPetContainer.layoutParams = lpContainer
            }
            else if(item.position != 0 && item.isClickPet) {
                val lpContainer = binding.userPetContainer.layoutParams
                lpContainer.width = defaultMarginLeft
                binding.userPetContainer.layoutParams = lpContainer
                val lp = FrameLayout.LayoutParams(heightBig, heightBig)
                lp.gravity = Gravity.BOTTOM or Gravity.RIGHT
                binding.petImageSmall.layoutParams = lp

            }
            else {
                val lp = FrameLayout.LayoutParams(heightSmall, heightSmall)
                lp.gravity = Gravity.BOTTOM or Gravity.RIGHT
                binding.petImageSmall.layoutParams = lp
                val lpContainer = binding.userPetContainer.layoutParams
                lpContainer.width = smallMarginLeft
                binding.userPetContainer.layoutParams = lpContainer
            }
            item.photos?.get(0)?.let { binding.petImageSmall.loadImage(it.url) }
        }
    }

    override fun onBind(item: UserPet, payloads: List<Any>) {
        super.onBind(item, payloads)
    }

}
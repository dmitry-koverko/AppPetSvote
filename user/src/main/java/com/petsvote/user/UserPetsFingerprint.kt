package com.petsvote.user

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.petsvote.core.adapter.BaseViewHolder
import com.petsvote.core.adapter.Item
import com.petsvote.core.adapter.ItemFingerprint
import com.petsvote.core.ext.getMonthOnYear
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.ui.ext.loadUrl
import com.petsvote.user.databinding.ItemUserPetBigBinding


class UserPetsFingerprint(
    private val onClick: (UserPet) -> Unit
) :
    ItemFingerprint<ItemUserPetBigBinding, UserPet> {

    override fun isRelativeItem(item: Item) = item is UserPet

    override fun getLayoutId() = R.layout.item_user_pet_big

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemUserPetBigBinding, UserPet> {
        val binding = ItemUserPetBigBinding.inflate(layoutInflater, parent, false)
        return UserPetViewHolder(binding, onClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UserPet>() {

        override fun areItemsTheSame(oldItem: UserPet, newItem: UserPet) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserPet, newItem: UserPet) = oldItem == newItem

    }

    class UserPetViewHolder(
        binding: ItemUserPetBigBinding,
        val onClick: (UserPet) -> Unit
    ) : BaseViewHolder<ItemUserPetBigBinding, UserPet>(binding) {

        override fun onBind(item: UserPet) {
            super.onBind(item)
            with(binding) {
                item.photos?.get(0)?.url?.let { binding.petImg.loadUrl(it) }
                var title = "${item.name}, ${item.bdate.let { binding.root.context.getMonthOnYear(it) }}"
                val text = SpannableStringBuilder("$title *").apply {
                    setSpan(
                        ImageSpan(
                            binding.root.context,
                            if(item.sex == "MALE") com.petsvote.ui.R.drawable.ic_icon_sex_male
                            else com.petsvote.ui.R.drawable.ic_icon_sex_female,
                            ImageSpan.ALIGN_BASELINE
                        ), title.length + 1, title.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
                binding.nameUserPet.text = text
                binding?.frame?.setOnClickListener {
                    onClick(item)
                }
            }
        }

    }
}

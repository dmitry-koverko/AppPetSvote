package com.petsvote.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.ui.textview.SimpleSFTextView

class ItemCardRating @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var screenWidth = context.resources.displayMetrics.widthPixels
    private var screenHeight = context.resources.displayMetrics.heightPixels
    private var margin = context.resources.displayMetrics.density * 12

    private var itemHeight = screenHeight * 0.36
    private var itemTopHeight = screenHeight * 0.5
    private var itemWidth = screenWidth / 2 - margin
    private var inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    init {
        inflater.inflate(R.layout.item_card_rating, this@ItemCardRating, true)
    }

    fun setType(type: RatingPetItemType) {
        when (type) {
            RatingPetItemType.DEFAULT -> setDefaultLP()
            RatingPetItemType.TOP -> setTopLP()
            RatingPetItemType.NULLABLE -> setNullableLP()
            RatingPetItemType.ADDPET -> setAppPetLP()
            RatingPetItemType.TOPADDPET -> setTopAppPetLP()
        }
    }

    fun setText(text: String){
        findViewById<SimpleSFTextView>(R.id.name).text = text
    }

    private fun setTopLP() {

        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, itemTopHeight.toInt())
    }

    private fun setNullableLP() {

        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(0, 0)

    }

    private fun setDefaultLP() {

        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(itemWidth.toInt(), itemHeight.toInt())

    }

    private fun setAppPetLP() {
        this.removeAllViews()
        setDefaultLP()
        inflater.inflate(R.layout.item_add_pet, this@ItemCardRating, true)

    }

    private fun setTopAppPetLP() {
        this.removeAllViews()
        setTopLP()
        inflater.inflate(R.layout.item_add_pet, this@ItemCardRating, true)

    }

    private fun ImageView.setMask(isUserPet: Boolean) {
        val icon = when (isUserPet) {
            true -> R.drawable.linear_mask_user
            false -> R.drawable.linear_mask_default
        }
        setImageResource(icon)
    }
}
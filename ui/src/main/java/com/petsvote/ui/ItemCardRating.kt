package com.petsvote.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.petsvote.domain.entity.pet.RatingPetItemType

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

}
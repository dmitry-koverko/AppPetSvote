package com.petsvote.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.ui.ext.loadFromResources
import com.petsvote.ui.ext.loadUrl
import com.petsvote.ui.ext.loadUrlListeners
import com.petsvote.ui.textview.SFTextView
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

    private var maskImage: ImageView?
    private var carImage: ImageView?
    private var nameTextView: TextView?
    private var locationTextView: TextView?
    private var corona: ImageView?

    init {
        inflater.inflate(R.layout.item_card_rating, this@ItemCardRating, true)
        maskImage = findViewById<ImageView>(R.id.mask)
        carImage = findViewById<ImageView>(R.id.image)
        nameTextView = findViewById<TextView>(R.id.name)
        locationTextView = findViewById<TextView>(R.id.location)
        corona = findViewById<ImageView>(R.id.corona)
    }
//
//    fun setType(type: RatingPetItemType) {
//        when (type) {
//            RatingPetItemType.DEFAULT -> setDefaultLP()
//            RatingPetItemType.TOP -> setTopLP()
//            RatingPetItemType.NULLABLE -> setNullableLP()
//            RatingPetItemType.ADDPET -> setAppPetLP()
//            RatingPetItemType.TOPADDPET -> setTopAppPetLP()
//        }
//    }

    fun setText(text: String) {
        findViewById<SimpleSFTextView>(R.id.name).text = text
    }

    fun setTopLP() {
        removeAllViews()
        inflater.inflate(R.layout.item_card_rating, this@ItemCardRating, true)
        maskImage = findViewById<ImageView>(R.id.mask)
        carImage = findViewById<ImageView>(R.id.image)
        nameTextView = findViewById<TextView>(R.id.name)
        locationTextView = findViewById<TextView>(R.id.location)
        corona = findViewById<ImageView>(R.id.corona)
        var lpTop = MarginLayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, itemTopHeight.toInt())
        lpTop.rightMargin = dpToPx(8f, context)
        findViewById<FrameLayout>(R.id.root).layoutParams = lpTop


        var lpName = nameTextView?.layoutParams as MarginLayoutParams
        lpName.leftMargin = com.petsvote.ui.dpToPx(16f, context)
        nameTextView?.layoutParams = lpName

        var lpLocation = locationTextView?.layoutParams as MarginLayoutParams
        lpLocation.leftMargin = com.petsvote.ui.dpToPx(16f, context)
        locationTextView?.layoutParams = lpLocation

    }

    fun setNullableLP() {
        removeAllViews()
        inflater.inflate(R.layout.item_card_rating, this@ItemCardRating, true)

        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(0, 0)

    }

    fun setDefaultLP() {
        removeAllViews()
        inflater.inflate(R.layout.item_card_rating, this@ItemCardRating, true)
        maskImage = findViewById<ImageView>(R.id.mask)
        carImage = findViewById<ImageView>(R.id.image)
        nameTextView = findViewById<TextView>(R.id.name)
        locationTextView = findViewById<TextView>(R.id.location)
        corona = findViewById<ImageView>(R.id.corona)
        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(itemWidth.toInt(), itemHeight.toInt())

    }

    fun setAppPetLP() {
        this.removeAllViews()
        inflater.inflate(R.layout.item_add_pet, this@ItemCardRating, true)

        findViewById<FrameLayout>(R.id.root).layoutParams =
            LayoutParams(itemWidth.toInt(), itemHeight.toInt())

        initSFTextViewParams()

    }

    fun setTopAppPetLP() {
        this.removeAllViews()

        inflater.inflate(R.layout.item_add_pet, this@ItemCardRating, true)

        var lpTop = MarginLayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, itemTopHeight.toInt())
        lpTop.rightMargin = dpToPx(8f, context)

        findViewById<FrameLayout>(R.id.root).layoutParams = lpTop
        initSFTextViewParams()
    }

    private fun initSFTextViewParams() {
        findViewById<SFTextView>(R.id.sfClick).animation = true
    }

    fun setImageCat(url: String) {
        carImage?.loadUrl(url)
    }

    fun setName(name: String){
        nameTextView?.text = name
    }

    fun setLocation(location: String){
        locationTextView?.text = location
    }

    fun setMask(isUserPet: Boolean) {
        val icon = when (isUserPet) {
            true -> R.drawable.linear_mask_user
            false -> R.drawable.linear_mask_default
        }
        maskImage?.loadFromResources(icon)
    }

    fun setCorona(isShow: Boolean = false){
        corona?.visibility = if(isShow) View.VISIBLE else View.GONE
    }
}
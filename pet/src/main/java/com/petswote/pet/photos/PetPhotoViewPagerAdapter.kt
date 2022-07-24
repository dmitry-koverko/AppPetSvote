package com.petswote.pet.photos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.ui.ScaleImageView
import com.petsvote.ui.urlToBitmapGlide
import com.petswote.pet.R
import com.petswote.pet.databinding.ItemPetPhotoViewPagerBinding


class PetPhotoViewPagerAdapter(private var photos: List<String>, private var context: Context) :
    RecyclerView.Adapter<PetPhotoViewPagerAdapter.MyViewHolder>() {

    private val TAG = PetPhotoViewPagerAdapter::class.java.name
    var mPetPhotoViewPagerAdapterListener: PetPhotoViewPagerAdapterListener? = null
    var currentImage: ScaleImageView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPetPhotoViewPagerBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.item_pet_photo_view_pager, parent, false)
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class MyViewHolder(private val binding: ItemPetPhotoViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(photo: String){
            binding.image.context.urlToBitmapGlide(photo, binding.image)
            binding.image.setOnUpDownListener(object : ScaleImageView.OnUpDownListener {
                override fun up() {
                    mPetPhotoViewPagerAdapterListener?.show()
                }

                override fun down() {
                    mPetPhotoViewPagerAdapterListener?.hide()
                }

                override fun tap() {
                    mPetPhotoViewPagerAdapterListener?.tap()
                }


            })

            currentImage = binding.image

        }
    }

    interface PetPhotoViewPagerAdapterListener{
        fun hide()
        fun show()
        fun tap()
    }

}
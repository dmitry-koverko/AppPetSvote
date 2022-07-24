package com.petswote.pet.photos

import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.getMonthOnYear
import com.petsvote.dialog.ComplaintSuccessDialog
import com.petsvote.dialog.PetPhotoMoreDialogFragment
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.user.Photo
import com.petsvote.ui.SharedView
import com.petsvote.ui.createBitmapFromView
import com.petsvote.ui.ext.spacing4Format
import com.petsvote.ui.ext.spacingFormat
import com.petsvote.ui.loadImage
import com.petsvote.ui.showShareIntent
import com.petsvote.ui.textview.SimpleSFTextView
import com.petswote.pet.R
import com.petswote.pet.databinding.FragmentPetPhotosBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PetPhotosFragment: BaseFragment(R.layout.fragment_pet_photos),
    PetPhotoMoreDialogFragment.PetPhotoMoreDialogFragmentListener,
    PetPhotoViewPagerAdapter.PetPhotoViewPagerAdapterListener {

    private lateinit var binding: FragmentPetPhotosBinding
    private var adapter: PetPhotoViewPagerAdapter? = null

    companion object{
        fun newInstance(string: String?, position: Int, location: String): PetPhotosFragment {
            val args = Bundle()
            args.putString("pet", string)
            args.putInt("position", position)
            args.putString("location", location)
            val fragment = PetPhotosFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPetPhotosBinding.bind(view)
        val bundle: Bundle = arguments ?: Bundle()
        var currentPet = (Json.decodeFromString(bundle.getString("pet", "")) as Pet)
        val list: List<Photo> = currentPet.photos
        val position = bundle.getInt("position")
        val location = bundle.getString("location")

        val listPhotoString = mutableListOf<String>()
        for (i in list)
            listPhotoString.add(i.url)

        var textSlider =
            "${position + 1} " + getString(com.petsvote.ui.R.string.from) + " ${listPhotoString.size}"
        binding.slideCounter.text = textSlider

        adapter = PetPhotoViewPagerAdapter(listPhotoString, requireContext())
        binding.viewPager.adapter = adapter
        binding.viewPager.setPageTransformer { page, position ->
            var textSlider =
                "${binding.viewPager.currentItem + 1} " + getString(com.petsvote.ui.R.string.from) + " ${listPhotoString.size}"
            binding.slideCounter.text = textSlider
        }
        binding.viewPager.setCurrentItem(position, false)
        adapter?.mPetPhotoViewPagerAdapterListener = this


        binding.more.setOnClickListener {
            var dialogMore = PetPhotoMoreDialogFragment()
            dialogMore.mPetPhotoMoreDialogFragmentListener = this
            dialogMore.show(childFragmentManager, "PetPhotoMoreDialogFragment")
        }

        binding.sharePetInfo.setOnClickListener {
            createShareBinding(
                listPhotoString[binding.viewPager.currentItem],
                currentPet,
                location ?: "",
                adapter?.currentImage?.viewBitmap)
        }

        binding.close.setOnClickListener {
            activity?.setResult(RESULT_OK)
            activity?.finish()
        }

    }

    private fun createShareBinding(s: String, pet: Pet, location: String, bitmap: Bitmap?) {
        var view = SharedView(requireContext())
        var lp = ConstraintLayout.LayoutParams(
            800,
            1100
        )
        view.visibility = View.VISIBLE
        view.layoutParams = lp

        var title = "${pet.name}, ${pet.bdate.let { context?.getMonthOnYear(it) }}"
        val googleText = SpannableStringBuilder("$title *").apply {
            setSpan(
                ImageSpan(
                    requireContext(),
                    if(pet.sex == "MALE") com.petsvote.ui.R.drawable.ic_icon_sex_male
                    else com.petsvote.ui.R.drawable.ic_icon_sex_female,
                    ImageSpan.ALIGN_BASELINE
                ), title.length + 1, title.length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        view.findViewById<SimpleSFTextView>(com.petsvote.ui.R.id.shareName).text = googleText
        view.findViewById<SimpleSFTextView>(com.petsvote.ui.R.id.shareId).spacing4Format(pet.pet_id)
        view.findViewById<SimpleSFTextView>(com.petsvote.ui.R.id.shareLocate).text = location
        bitmap?.let { view.findViewById<ImageView>(com.petsvote.ui.R.id.shareImage).setImageBitmap(it) }

        binding.viewContainer.addView(view)
        binding.viewContainer.addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
            override fun onLayoutChange(
                p0: View?,
                p1: Int,
                p2: Int,
                p3: Int,
                p4: Int,
                p5: Int,
                p6: Int,
                p7: Int,
                p8: Int
            ) {
                var bm = createBitmapFromView(binding.viewContainer)
                view.visibility = View.GONE
                if (bm != null) {
                    showShareIntent(bm)
                }
            }

        })

    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
    }

    override fun hide() {
        binding.viewPager.isUserInputEnabled = false
        binding.topContainer.visibility = View.GONE
        binding.bottomContainer.visibility = View.GONE
    }

    override fun show() {
        binding.viewPager.isUserInputEnabled = true
    }

    override fun tap() {
        binding.topContainer.visibility = View.VISIBLE
        binding.bottomContainer.visibility = View.VISIBLE
    }

    override fun complaint() {
        binding.container.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        var bm = createBitmapFromView(binding.container)
        bm?.let { ComplaintSuccessDialog(it).show(childFragmentManager, "ComplaintSuccessDialog") }
    }


    override fun initObservers() {

    }
}
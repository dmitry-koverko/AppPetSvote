package com.petsvote.ui.textview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.petsvote.ui.R

@SuppressLint("ResourceType")
class DefaultText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    private var typeFaceSF = ResourcesCompat.getFont(context, R.font.defaultttf)
    init {
        includeFontPadding = false
        typeface = typeFaceSF
    }

}
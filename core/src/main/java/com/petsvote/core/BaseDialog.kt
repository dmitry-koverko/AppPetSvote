package com.petsvote.core

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialog(
    private val resId: Int,
    private val isMath: Boolean = false,
    private val defaultMargin: Boolean = true
) : DialogFragment(resId) {


    var widthFragment = 0f
    var heightFragment = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialog)
    }

    override fun onResume() {
        super.onResume()
        if (dialog == null) return
        val window = dialog!!.window ?: return
        var dm =  resources.displayMetrics

        widthFragment = dm.widthPixels.toFloat()
        heightFragment = dm.heightPixels.toFloat()

        if(defaultMargin){
            widthFragment -= dm.density * 16
            heightFragment -= dm.density * 16
        }

        val params: WindowManager.LayoutParams = window.attributes
        params.width = widthFragment.toInt()
        if (isMath) {
            params.height = heightFragment.toInt()
        }
        window.attributes = params
    }
}
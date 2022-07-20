package com.petsvote.ui.ext

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun TextView.spacingFormat(value: Int, subValue: String = "") {
    val value = DecimalFormat("###,###", DecimalFormatSymbols().apply {
        groupingSeparator = ' '
    }).format(value)
    var result = "$value $subValue"
    this.text = result
}

fun Fragment.showSnackBar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
}

fun Fragment.sharePet(petId: Int) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "https://go.petsvote.app/vmpe/share?/ID=$petId"//TODO to release
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}

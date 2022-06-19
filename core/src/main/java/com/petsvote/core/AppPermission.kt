package com.petsvote.core

import android.Manifest

sealed class AppPermission(
    val permissionName: String, val requestCode: Int, val deniedMessageId: Int, val explanationMessageId: Int
) {
    companion object {
        val permissions: List<AppPermission> by lazy {
            listOf(
                ACCESS_FINE_LOCATION
            )
        }
    }

    object ACCESS_FINE_LOCATION : AppPermission(
        Manifest.permission.ACCESS_FINE_LOCATION, 42,
        R.string.permission_required_text, R.string.permission_required_text
    )
}
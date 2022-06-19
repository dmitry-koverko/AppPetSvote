package com.petsvote.core

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment

open class BaseDialog(
    private val resId: Int,
    private val isMath: Boolean = false,
    private val defaultMargin: Boolean = true
) : DialogFragment(resId) {

    private var RC_PERMISSION = 101
    private var CAM_PERMISSION = 102
    var CAMERA_PERMISSION = Manifest.permission.CAMERA
    var READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    var WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

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

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.requestPermissions(arrayOf(CAMERA_PERMISSION, READ_PERMISSION, WRITE_PERMISSION ), 102)
        }else{}
    }

    fun checkPermissionsWrite(): Boolean {
        return ((activity?.let { ActivityCompat.checkSelfPermission(it, WRITE_PERMISSION) }) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(requireActivity(), WRITE_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    fun checkCameraPermissions(): Boolean {
        return ((activity?.let { ActivityCompat.checkSelfPermission(it, CAMERA_PERMISSION) }) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(requireActivity(), CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    fun checkReadPermissions(): Boolean {
        return ((activity?.let { ActivityCompat.checkSelfPermission(it, READ_PERMISSION) }) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(requireActivity(), READ_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }
}
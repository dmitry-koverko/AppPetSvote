package com.petsvote.user.di

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.ui.*
import com.petsvote.user.R
import com.petsvote.user.databinding.SettingsUserProfileBinding
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SettingProfileFragment: DialogFragment(R.layout.settings_user_profile) {

    private val urlIG = "https://www.instagram.com/petsvote.app/"
    private val urlFB = "https://www.facebook.com/petsvotepage/"
    private val urlTW = "https://twitter.com/petsvotea"
    private val urlTG = "https://t.me/petsvote"
    private val urlVB = "https://invite.viber.com/?g2=AQAYi1kZYJYNZE59qwGSzxMKpTqMpvh0FXKzRpQ43X7jCovo0JYtv3nxsgmeu9dA"

    @Inject
    internal lateinit var viewModelFactory: Lazy<SettingsProfileViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: SettingsProfileViewModel by viewModels {
        viewModelFactory.get()
    }

    var binding: SettingsUserProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.petsvote.ui.R.style.MyDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SettingsUserProfileBinding.bind(view)

        binding?.close?.setOnClickListener {
            dismiss()
        }

        binding?.instagram?.setOnClickListener {
            openUrl(urlIG)
        }
        binding?.facebook?.setOnClickListener {
            openUrl(urlFB)
        }

        binding?.twitter?.setOnClickListener {
            openUrl(urlTW)
        }

        binding?.telegram?.setOnClickListener {
            openUrl(urlTG)
        }

        binding?.viber?.setOnClickListener {
            openUrl(urlVB)
        }

        binding?.supportContainer?.setOnClickListener {
            it.isPressed = true
            sendSupport()
        }

        binding?.ratingContainer?.setOnClickListener {
            it.isPressed = true
            ratingApp()
        }

        binding?.profileContainer?.setOnClickListener {
            it.isPressed = true
            //activity?.let { it1 -> navigationUser.startUserProfile(it1) }
        }

        binding?.shareContainer?.setOnClickListener {
            it.isPressed = true
            shareApp()
        }

        binding?.switchNotify?.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setNotify(b)
        }

        lifecycleScope.launchWhenCreated { viewModel.getUserInfo() }
        lifecycleScope.launchWhenCreated { viewModel.getNotify() }

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.user.collect {
                it?.let {userInfo ->
                    userInfo.avatar?.let { ava ->
                        binding?.userImage?.loadImage(ava)
                    }
                    binding?.ownerName?.text = "${userInfo?.first_name} ${userInfo?.last_name}"
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.settingsNotify.collect {
                binding?.switchNotify?.isChecked = it
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (dialog == null) return
        val window = dialog!!.window ?: return
        val width = resources.displayMetrics.widthPixels - resources.displayMetrics.density * 16
        val params: WindowManager.LayoutParams = window.attributes
        params.width = width.toInt()
        window.attributes = params
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectSettingsProfile(this)
    }
}
package com.petsvote.domain.usecases.configuration.impl

import android.content.res.Configuration
import android.os.Build
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import javax.inject.Inject

class GetLocaleLanguageCodeUseCase @Inject constructor(
    private var configuration: Configuration
) : GetLocaleLanguageCodeUseCase {

    override fun getLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales[0].country
        } else {
            "en"
        }
    }

}
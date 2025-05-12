package com.nca.yourdentist.presentation.screens.common.auth.select_type

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.LanguageConstants

class SelectTypeViewModel(
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    fun setUpAppLanguage() {
        val language = preferencesHelper.fetchString(Constant.CURRENT_LANGUAGE)
        if (language.isEmpty()) {
            preferencesHelper.putString(Constant.CURRENT_LANGUAGE, LanguageConstants.ENGLISH)
        }
    }

}
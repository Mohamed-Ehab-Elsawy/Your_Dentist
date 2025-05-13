package com.nca.yourdentist.presentation.screens.common.auth.select_type

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.local.usecase.FetchCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.PutCurrentLanguageUseCase

class SelectTypeViewModel(
    private val fetchCurrentLanguage: FetchCurrentLanguageUseCase,
    private val putCurrentLanguage: PutCurrentLanguageUseCase
) : ViewModel() {
    fun setUpAppLanguage() {
        val language = fetchCurrentLanguage.invoke()
        if (language.isEmpty()) putCurrentLanguage.invoke(language)
    }
}
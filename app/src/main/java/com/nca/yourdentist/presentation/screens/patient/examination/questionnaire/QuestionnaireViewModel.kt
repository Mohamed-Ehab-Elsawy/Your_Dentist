package com.nca.yourdentist.presentation.screens.patient.examination.questionnaire

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.model.FinalResult
import com.nca.yourdentist.utils.AppProviders
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.File

class QuestionnaireViewModel(
    private val preferencesHelper: PreferencesHelper,
    // TODO: add usecase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

    fun uploadXray(photoFile: File) {
        val finalResult = FinalResult(originalXRay = photoFile)
        AppProviders.finalResult = finalResult
        Log.e("QuestionnaireViewModel", "Selected File Path: ${photoFile.absolutePath}")
    }
}
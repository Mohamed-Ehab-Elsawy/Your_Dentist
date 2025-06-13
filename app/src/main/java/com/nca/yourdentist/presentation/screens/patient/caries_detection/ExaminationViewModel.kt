package com.nca.yourdentist.presentation.screens.patient.caries_detection

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.Questionnaire
import com.nca.yourdentist.data.models.response.CariesDetectionResponse
import com.nca.yourdentist.domain.remote.usecase.detect_caries.DetectCariesUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class ExaminationViewModel(
    private val detectCariesUseCase: DetectCariesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<CariesDetectionResponse>>(UiState.Idle)
    val uiState: StateFlow<UiState<CariesDetectionResponse>> = _uiState

    var uploadedXRay: File? = null
        private set
    var questionnaire: List<Questionnaire> = emptyList()
        private set

    fun detectCaries(imageFile: File, context: Context) {
        try {
            _uiState.value = UiState.Loading
            viewModelScope.launch {
                uploadedXRay = imageFile
                val result = detectCariesUseCase(imageFile, context)
                _uiState.value = UiState.Success(result)

            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }
    }

    fun updateQuestionnaire(q: List<Questionnaire>) {
        questionnaire = q
        Log.e("ExaminationViewModel", "updateQuestionnaire: $questionnaire")
    }
}
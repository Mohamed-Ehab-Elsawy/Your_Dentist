package com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.domain.remote.usecase.booking.FetchDentistUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectDentistViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val fetchDentist: FetchDentistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Dentist>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Dentist>>> = _uiState

    var selectedCity = mutableIntStateOf(0)
        private set
    var selectedArea = mutableIntStateOf(0)
        private set

    private fun fetchDentist() {
        _uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                val dentists = fetchDentist.invoke(selectedCity.intValue, selectedArea.intValue)
                dentists.onSuccess {
                    if (it.isNotEmpty())
                        _uiState.value = UiState.Success(it)
                    else
                        _uiState.value =
                            UiState.Error(Throwable(message = "No Dentists in this area"))
                }
                dentists.onFailure { t ->
                    _uiState.value = UiState.Error(t)
                }
            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }

    }

    fun onCityChange(newCity: Int) {
        selectedCity.intValue = newCity
    }

    fun onAreaChange(newArea: Int) {
        selectedArea.intValue = newArea
        fetchDentist()
    }

    fun activeLanguage(): String = preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)

}
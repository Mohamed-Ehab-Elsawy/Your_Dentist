package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.data.models.users.Dentist

@Composable
fun AvailableDentistsSection(
    modifier: Modifier = Modifier,
    dentists: List<Dentist>,
    activeLanguage: String,
    onChooseDentist: (Dentist) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dentists.size) { index ->
            DentistItem(
                dentist = dentists[index],
                activeLanguage = activeLanguage
            ) { dentist ->
                onChooseDentist.invoke(dentist)
            }
        }
    }
}
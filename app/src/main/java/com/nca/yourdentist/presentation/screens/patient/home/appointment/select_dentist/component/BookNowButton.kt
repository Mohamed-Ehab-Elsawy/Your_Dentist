package com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist.component

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.utils.AppProviders.dentist

@Composable
fun BookNowButton(modifier: Modifier = Modifier, onBookNowClick: (Dentist) -> Unit) {
    // Book Now Button
    CustomButton(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        onClick = {
            Log.d("BookNowClick", "Clicked on ${dentist?.name}")
            onBookNowClick.invoke(dentist ?: Dentist())
        },
        text = stringResource(R.string.book_now),
        enabled = true
    )
}
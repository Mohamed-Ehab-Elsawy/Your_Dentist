package com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.nca.yourdentist.utils.AppProviders.dentist
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookAppointmentScreen(
    navController: NavController,
    viewModel: BookAppointmentViewModel = koinViewModel()
) {

    Text(text = "Booking Appointment for Dr. ${dentist?.name ?: "UNKNOWN"}")
}
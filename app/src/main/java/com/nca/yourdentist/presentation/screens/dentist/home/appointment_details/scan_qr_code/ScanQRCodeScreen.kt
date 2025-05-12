package com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.scan_qr_code

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.navigation.DentistScreens
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onSurfaceLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.scan_qr_code.components.QRScannerView
import com.nca.yourdentist.utils.Constant

@Composable
fun ScanQRCodeScreen(navController: NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var scannerPaused by remember { mutableStateOf(false) }
    val appointment =
        navController.previousBackStackEntry?.savedStateHandle?.get<Appointment>(Constant.APPOINTMENT)
            ?: Appointment()

    Box {
        if (!scannerPaused) {
            QRScannerView { result ->
                val id = appointment.patientId?.lowercase()
                Log.e("TAG", "Scanned: ${appointment.copy()}")
                Log.e("TAG", "Scanned: $result")
                Log.e("TAG", "Actual id: $id")
                scannerPaused = true
                if (id == result) {
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    navController.navigate(DentistScreens.AppointmentDetails.route)
                } else
                    showDialog = true
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Prevent dismissal */ },
                containerColor = surfaceLight,
                titleContentColor = errorLight,
                textContentColor = onSurfaceLight,
                title = { Text(text = "Invalid QR code") },
                text = { Text(text = "Wrong patient QR code.") },
                confirmButton = {
                    CustomButton(text = "Go Home", onClick = {
                        navController.navigate(DentistScreens.Home.route)
                    })
                }
            )
        }
    }
}
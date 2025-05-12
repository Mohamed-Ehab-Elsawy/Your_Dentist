package com.nca.yourdentist.presentation.screens.dentist.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.DentistScreens
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.screens.dentist.home.component.AppointmentsSection
import com.nca.yourdentist.presentation.screens.dentist.home.component.CalendarSection
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.capitalizeFirstLetter
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DentistHomeScreen(
    navController: NavController,
    viewModel: DentistHomeViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }
    var currentWeekIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.home))
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = stringResource(R.string.welcome_dr) + " " +
                            AppProviders.dentist?.name!!.capitalizeFirstLetter(),
                    style = AppTypography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                CalendarSection(
                    today = today,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(Modifier.height(16.dp))

                AppointmentsSection(
                    selectedDate = selectedDate,
                    onAppointmentClick = { appointment ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Constant.APPOINTMENT,
                            appointment
                        )
                        navController.navigate(DentistScreens.ScanQRCodeScreen.route)
                    }
                )
            }
        }
    }
}
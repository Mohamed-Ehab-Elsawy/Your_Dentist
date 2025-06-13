package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.navigation.PatientScreens.BookAppointment
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryContainerLight
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component.AvailableDentistsSection
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component.LocationSection
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.LanguageConstants
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectDentistScreen(
    navController: NavController,
    vm: SelectDentistViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val uiState by vm.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var activeLanguage by remember { mutableStateOf(LanguageConstants.ENGLISH) }
    var selectedCity by remember { mutableIntStateOf(0) }
    var selectedArea by remember { mutableIntStateOf(0) }
    var dentists by remember { mutableStateOf<List<Dentist>>(emptyList()) }
    var report by remember { mutableStateOf(Report()) }


    LaunchedEffect(Unit) {
        report =
            navController.previousBackStackEntry?.savedStateHandle?.get<Report>(Constant.REPORT)
                ?: Report()
        Log.e("SelectDentistScreen", "report: $report")
        activeLanguage = vm.activeLanguage()
    }

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(R.string.select_dentist)) },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = primaryContainerLight,
                    contentColor = onPrimaryContainerLight
                )
            }
        }
    ) { paddingValues ->
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
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationSection(
                    selectedCityKey = selectedCity,
                    selectedAreaKey = selectedArea,
                    onCityChange = {
                        selectedCity = it
                        vm.onCityChange(it)
                    },
                    onAreaChange = {
                        selectedArea = it
                        vm.onAreaChange(it)
                    }
                )

                AvailableDentistsSection(
                    modifier = Modifier.padding(top = 16.dp),
                    dentists = dentists,
                    activeLanguage = activeLanguage,
                    onChooseDentist = { dentist ->
                        navController.currentBackStackEntry?.savedStateHandle[Constant.DENTIST] =
                            dentist
                        navController.currentBackStackEntry?.savedStateHandle[Constant.REPORT] =
                            report
                        navController.navigate(BookAppointment.route)
                    }
                )
            }
        }
    }

    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()

        is UiState.Success -> {
            LaunchedEffect(uiState) {
                val newList = (uiState as UiState.Success<List<Dentist>>).data
                dentists =
                    if (!newList.isEmpty()) newList
                    else emptyList()
            }
        }

        is UiState.Error -> {
            val errorThrowable = (uiState as UiState.Error).t
            LaunchedEffect(Unit) {
                if (errorThrowable.message == "No Dentists in this area")
                    snackBarHostState.showSnackbar(context.getString(R.string.no_dentists_in_this_area))
                else
                    snackBarHostState.showSnackbar(errorThrowable.localizedMessage ?: "")
            }
        }
    }
}
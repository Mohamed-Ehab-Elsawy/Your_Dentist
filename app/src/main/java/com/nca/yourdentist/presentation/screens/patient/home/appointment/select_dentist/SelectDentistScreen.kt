package com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.CityArea.Companion.cairoAreasMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.citiesMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.gizaAreasMap
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.navigation.PatientScreens.BookAppointment
import com.nca.yourdentist.presentation.component.ui.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.CustomMapDropDownMenu
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist.component.DentistItem
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectDentistScreen(
    navController: NavController,
    viewModel: SelectDentistViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var selectedCity by remember { mutableIntStateOf(0) }
    var selectedArea by remember { mutableIntStateOf(0) }
    var dentists by remember { mutableStateOf<List<Dentist>>(emptyList()) }
    val context = LocalContext.current
    var activeLanguage by remember { mutableStateOf("en") }

    LaunchedEffect(Unit) {
        activeLanguage = viewModel.activeLanguage()
    }

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(R.string.results)) },
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.choose_your_current_city_and_area),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryLight,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                CustomMapDropDownMenu(
                    options = citiesMap,
                    selectedKey = viewModel.selectedCity.intValue,
                    label = stringResource(R.string.city),
                    onValueChange = { viewModel.onCityChange(it) }
                )

                Spacer(Modifier.height(4.dp))

                CustomMapDropDownMenu(
                    options =
                        if (viewModel.selectedCity.intValue == 1) cairoAreasMap
                        else if (viewModel.selectedCity.intValue == 2) gizaAreasMap
                        else emptyMap(),
                    selectedKey = viewModel.selectedArea.intValue,
                    label = stringResource(R.string.area),
                    onValueChange = { viewModel.onAreaChange(it) }
                )

                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(dentists.size) { index ->
                        DentistItem(
                            dentist = dentists[index],
                            activeLanguage = activeLanguage
                        ) { dentist ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                Constant.DENTIST,
                                dentist
                            )
                            navController.navigate(BookAppointment.route)
                        }
                    }
                }
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
                coroutineScope.launch {
                    if (errorThrowable.message == "No Dentists in this area")
                        snackBarHostState.showSnackbar(context.getString(R.string.no_dentists_in_this_area))
                    else
                        snackBarHostState.showSnackbar(errorThrowable.localizedMessage ?: "")
                }
            }
        }
    }
}
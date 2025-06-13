package com.nca.yourdentist.presentation.screens.common.select_type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.AuthScreens
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import com.nca.yourdentist.presentation.screens.common.select_type.component.UserTypeCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectUserTypeScreen(
    navController: NavController,
    viewModel: SelectTypeViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setUpAppLanguage()
    }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.choose_your_role))
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = white)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item { Spacer(Modifier.height(32.dp)) }
            item {
                Text(
                    text = stringResource(R.string.welcome__),
                    style = AppTypography.headlineLarge,
                    color = primaryLight
                )
            }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Text(
                    text = stringResource(R.string.tell_us_a_bit_about_yourself_to_get_started_with_our_dental_care_platform),
                    style = AppTypography.titleSmall,
                    color = secondaryLight,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(Modifier.height(32.dp))
            }
            item {
                UserTypeCard(
                    iconRes = R.drawable.ic_patient,
                    title = "Patient",
                    description = "I have teeth issues and need help.",
                    onClick = { navController.navigate(AuthScreens.PatientLogin.route) }
                )
            }
            item { Spacer(Modifier.height(48.dp)) }
            item {
                UserTypeCard(
                    iconRes = R.drawable.ic_dentist,
                    title = "Dentist",
                    description = "I am a dentist.",
                    onClick = { navController.navigate(AuthScreens.DentistLogin.route) }
                )
            }
        }
    }
}
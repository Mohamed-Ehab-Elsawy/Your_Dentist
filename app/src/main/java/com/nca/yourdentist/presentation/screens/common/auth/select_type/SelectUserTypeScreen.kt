package com.nca.yourdentist.presentation.screens.common.auth.select_type

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.MainScreens
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectUserTypeScreen(
    navController: NavController, viewModel: SelectTypeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setUpAppLanguage()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Patient Selection
            UserTypeCard(
                title = stringResource(id = R.string.login_as_patient),
                imageRes = R.drawable.img_patient,
                onClick = { navController.navigate(MainScreens.PatientLogin.route) },
                modifier = Modifier.weight(1f) // Distribute space equally
            )

            // Dentist Selection
            UserTypeCard(
                title = stringResource(id = R.string.login_as_dentist),
                imageRes = R.drawable.img_dentist,
                onClick = { navController.navigate(MainScreens.DentistLogin.route) },
                modifier = Modifier.weight(1f) // Distribute space equally
            )
        }
    }
}

@Composable
fun UserTypeCard(
    modifier: Modifier = Modifier,
    title: String, imageRes: Int, onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = white
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.6f)
            )
            Text(
                text = title,
                style = AppTypography.displayLarge,
                color = primaryLight,
                textAlign = TextAlign.Center
            )
        }
    }
}
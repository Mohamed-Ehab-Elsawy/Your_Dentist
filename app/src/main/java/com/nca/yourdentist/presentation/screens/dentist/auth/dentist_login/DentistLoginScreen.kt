package com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.ProgressIndicator
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.text_fields.EmailTextField
import com.nca.yourdentist.presentation.component.ui.text_fields.PasswordTextField
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.utils.UiState
import com.nca.yourdentist.utils.providers.DentistProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun DentistLoginScreen(
    navController: NavController,
    viewModel: DentistLoginViewModel = koinViewModel(),
    preferencesHelper: PreferencesHelper = getKoin().get()
) {

    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collect { message ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            }
        }
    }

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.dentist_login),
                iconRes = R.drawable.ic_world,
                iconTint = primaryLight,
                onIconClick = {}
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = errorLight,
                    contentColor = onErrorLight
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
            verticalArrangement = Arrangement.Top // No extra spacing
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier.size(150.dp),
                    tint = primaryLight
                )
            }

            Spacer(modifier = Modifier.padding(top = 50.dp))

            EmailTextField(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChange,
                errorMessage = viewModel.emailError.value
            )

            PasswordTextField(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                errorMessage = viewModel.passwordError.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp)
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.login()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                enabled = uiState !is UiState.Loading
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = onPrimaryLight,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }

    }

    when (uiState) {
        is UiState.Loading -> {
            ProgressIndicator()
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                if (DentistProvider.dentist != null) {
                    preferencesHelper.saveDentist(DentistProvider.dentist!!)
                    navController.navigate(Screen.DentistHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        else -> {}
    }
}
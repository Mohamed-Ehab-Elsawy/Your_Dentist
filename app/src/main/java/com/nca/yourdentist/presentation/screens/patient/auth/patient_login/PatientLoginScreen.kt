package com.nca.yourdentist.presentation.screens.patient.auth.patient_login

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.CustomAppBar
import com.nca.yourdentist.presentation.component.PasswordTextField
import com.nca.yourdentist.presentation.component.ProgressIndicator
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.utils.UiState
import com.nca.yourdentist.utils.providers.PatientProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun PatientLoginScreen(
    navController: NavController,
    preferencesHelper: PreferencesHelper = getKoin().get(),
    viewModel: PatientLoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val serverClientId = stringResource(id = R.string.default_web_client_id)

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collect { message ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            }
        }
    }

    Scaffold(
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
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top // No extra spacing
        ) {
            CustomAppBar(
                title = stringResource(R.string.patient_login),
                iconRes = R.drawable.ic_world,
                iconTint = primaryLight,
                onIconClick = {}
            )

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
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
                OutlinedTextField(
                    value = viewModel.email.value,
                    onValueChange = viewModel::onEmailChange,
                    textStyle = TextStyle(
                        color = primaryLight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.e_mail),
                            style = MaterialTheme.typography.bodyLarge, color = primaryLight
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = viewModel.emailError.value != null,
                    supportingText = {
                        viewModel.emailError.value?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                )

                PasswordTextField(
                    value = viewModel.password.value,
                    onValueChange = viewModel::onPasswordChange,
                    errorMessage = viewModel.passwordError.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                )

                TextButton(
                    onClick = { navController.navigate(Screen.ForgetPassword.route) },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 0.dp) // Remove top padding
                ) {
                    Text(text = stringResource(R.string.forget_password), color = secondaryLight)
                }
                Button(
                    onClick = {
                        keyboardController?.hide() // Hide keyboard
                        viewModel.login() // Trigger login
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                    enabled = uiState !is UiState.Loading
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        color = onPrimaryLight,
                        fontSize = 18.sp
                    )
                }

                TextButton(
                    onClick = { navController.navigate(Screen.PatientSignup.route) },
                ) {
                    Text(
                        text = stringResource(R.string.create_new_account),
                        color = secondaryLight, fontSize = 16.sp
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.signInWithGoogle(context, serverClientId)
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = stringResource(R.string.log_in_with_google),
                            color = primaryLight,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            ProgressIndicator()
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                if (PatientProvider.patient != null) {
                    preferencesHelper.savePatient(PatientProvider.patient!!)
                    navController.navigate(Screen.PatientHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        else -> {}
    }
}
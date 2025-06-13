package com.nca.yourdentist.presentation.screens.common.auth.patient_signup

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.PatientActivity
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.customized.CustomStringDropDownMenu
import com.nca.yourdentist.presentation.component.ui.customized.EmailTextField
import com.nca.yourdentist.presentation.component.ui.customized.PasswordTextField
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.common.auth.patient_signup.component.BirthdatePicker
import com.nca.yourdentist.presentation.screens.common.auth.patient_signup.component.NameTextField
import com.nca.yourdentist.presentation.screens.common.auth.patient_signup.component.PhoneTextField
import com.nca.yourdentist.presentation.utils.Provider
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientSignupScreen(
    navController: NavController,
    viewModel: PatientSignupViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val autoFillManager = LocalAutofillManager.current

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = (stringResource(id = R.string.sign_up)),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                iconTint = primaryLight,
                onIconClick = { navController.popBackStack() }
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
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    NameTextField(
                        value = viewModel.name.value,
                        onValueChange = { viewModel.onNameChange(it) },
                        isError = viewModel.nameError.value != null,
                        errorText = viewModel.nameError.value ?: ""
                    )
                }

                item {
                    EmailTextField(
                        value = viewModel.email.value,
                        onValueChange = { viewModel.onEmailChange(it) },
                        errorMessage = viewModel.emailError.value
                    )
                }

                item {
                    CustomStringDropDownMenu(
                        options = listOf(
                            stringResource(id = R.string.male),
                            stringResource(id = R.string.female)
                        ),
                        value = viewModel.gender.value,
                        label = stringResource(id = R.string.gender),
                        onValueChange = { viewModel.onGenderChange(it) },
                        errorMessage = viewModel.genderError.value
                    )
                }

                item {
                    BirthdatePicker(
                        value = viewModel.birthdate.value,
                        onValueChange = { viewModel.onBirthdateChange(it) },
                        errorMessage = viewModel.birthdateError.value
                    )
                }

                item {
                    PhoneTextField(
                        value = viewModel.phoneNumber.value,
                        onValueChange = { viewModel.onPhoneNumberChange(it) },
                        isError = viewModel.phoneNumberError.value != null,
                        errorText = viewModel.phoneNumberError.value ?: ""
                    )
                }

                item {
                    PasswordTextField(
                        value = viewModel.password.value,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        errorMessage = viewModel.passwordError.value
                    )
                }

                // Confirm password
                item {
                    PasswordTextField(
                        value = viewModel.confirmPassword.value,
                        onValueChange = { viewModel.onConfirmPasswordChange(it) },
                        label = stringResource(id = R.string.confirm_your_password),
                        errorMessage = viewModel.confirmPasswordError.value
                    )
                }

                item {
                    CustomButton(
                        modifier = Modifier.padding(top = 24.dp),
                        text = stringResource(id = R.string.sign_up),
                        enabled = uiState !is UiState.Loading,
                        onClick = {
                            keyboardController?.hide()
                            autoFillManager?.commit()
                            viewModel.signup()
                        }
                    )
                }
            }
        }
    }

    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()
        is UiState.Success -> {
            LaunchedEffect(Unit) {
                if (Provider.patient != null) {
                    activity?.startActivity(
                        Intent(context, PatientActivity::class.java)
                    )
                    activity?.finish()
                }
            }
        }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        (uiState as UiState.Error).t.localizedMessage ?: "Error",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}
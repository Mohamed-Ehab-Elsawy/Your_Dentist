package com.nca.yourdentist.presentation.screens.patient.auth.patient_signup

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.BirthdatePicker
import com.nca.yourdentist.presentation.component.ui.ProgressIndicator
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.CustomStringDropDownMenu
import com.nca.yourdentist.presentation.component.ui.customized.EmailTextField
import com.nca.yourdentist.presentation.component.ui.customized.PasswordTextField
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.patient.PatientMainActivity
import com.nca.yourdentist.utils.AppProviders
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientSignupScreen(
    navController: NavController,
    viewModel: PatientSignupViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as? Activity

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
                title = (stringResource(id = R.string.sign_up)),
                iconRes =
                    if (viewModel.appLanguage() == Constant.ENGLISH_LANGUAGE_KEY)
                        R.drawable.ic_arrow_left
                    else
                        R.drawable.ic_arrow_right,
                iconTint = primaryLight, onIconClick = {
                    navController.popBackStack()
                }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.onNameChange(it) },
                    maxLines = 1,
                    label = { Text(stringResource(id = R.string.full_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    isError = viewModel.nameError.value != null,
                    supportingText = {
                        viewModel.nameError.value?.let { Text(it, color = errorLight) }
                    }
                )

                EmailTextField(
                    value = viewModel.email.value,
                    onValueChange = { viewModel.onEmailChange(it) },
                    errorMessage = viewModel.emailError.value
                )

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

                BirthdatePicker(
                    value = viewModel.birthdate.value,
                    onValueChange = { viewModel.onBirthdateChange(it) },
                    errorMessage = viewModel.birthdateError.value
                )

                OutlinedTextField(
                    value = viewModel.phoneNumber.value,
                    onValueChange = { viewModel.onPhoneNumberChange(it) },
                    maxLines = 1,
                    label = { Text(stringResource(id = R.string.phone_number)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    isError = viewModel.phoneNumberError.value != null,
                    supportingText = {
                        viewModel.phoneNumberError.value?.let {
                            Text(
                                it,
                                color = errorLight
                            )
                        }
                    }
                )

                PasswordTextField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    errorMessage = viewModel.passwordError.value
                )

                PasswordTextField(
                    value = viewModel.confirmPassword.value,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    label = stringResource(id = R.string.confirm_your_password),
                    errorMessage = viewModel.confirmPasswordError.value
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.signup()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                    enabled = uiState !is UiState.Loading
                ) {
                    Text(
                        stringResource(id = R.string.create_account),
                        color = onPrimaryLight,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
                if (AppProviders.patient != null) {
                    activity?.startActivity(
                        Intent(context, PatientMainActivity::class.java)
                    )
                    activity?.finish()
                }
            }
        }

        else -> {}
    }
}
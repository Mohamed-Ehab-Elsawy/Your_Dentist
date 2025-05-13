package com.nca.yourdentist.presentation.screens.common.auth.patient_login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.MainScreens
import com.nca.yourdentist.presentation.component.ui.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.customized.EmailTextField
import com.nca.yourdentist.presentation.component.ui.customized.PasswordTextField
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.screens.patient.PatientMainActivity
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientLoginScreen(
    navController: NavController,
    viewModel: PatientLoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val autoFillManager = LocalAutofillManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val serverClientId = stringResource(id = R.string.default_web_client_id)

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.patient_login),
                icon = ImageVector.vectorResource(R.drawable.ic_world),
                iconTint = primaryLight,
                onIconClick = {
                    viewModel.changeLanguage()
                    activity?.recreate()
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
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    tint = primaryLight
                )

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

                Text(
                    text = stringResource(R.string.forget_password),
                    color = secondaryLight,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(MainScreens.ForgetPassword.route)
                        }
                        .align(Alignment.Start)
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                CustomButton(
                    text = stringResource(R.string.login),
                    enabled = uiState !is UiState.Loading,
                    onClick = {
                        keyboardController?.hide()
                        autoFillManager?.commit()
                        viewModel.login()
                    }
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                Text(
                    text = stringResource(R.string.create_new_account),
                    color = secondaryLight,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { navController.navigate(MainScreens.PatientSignup.route) }
                        .align(Alignment.Start)
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

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
                            color = secondaryLight,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }

    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()
        is UiState.Success -> {
            LaunchedEffect(Unit) {
                if (AppProviders.patient != null)
                    activity?.apply {
                        startActivity(Intent(context, PatientMainActivity::class.java))
                        finish()
                    }
            }
        }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                val message = if ((uiState as UiState.Error).t.message == Constant.USER_TYPE_ERROR)
                    context.getString(R.string.user_type_is_not_patient)
                else
                    (uiState as UiState.Error).t.localizedMessage
                        ?: context.getString(R.string.error)

                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}
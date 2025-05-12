package com.nca.yourdentist.presentation.screens.common.auth.dentist_login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
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
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.presentation.component.ui.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.EmailTextField
import com.nca.yourdentist.presentation.component.ui.customized.PasswordTextField
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.dentist.DentistMainActivity
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun DentistLoginScreen(
    navController: NavController,
    viewModel: DentistLoginViewModel = koinViewModel(),
    preferencesHelper: PreferencesHelper = getKoin().get()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val autoFillManager = LocalAutofillManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.dentist_login),
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
            Modifier
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
                    .padding(vertical = 16.dp, horizontal = 20.dp)
                    .fillMaxSize(),
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
                        autoFillManager?.commit()
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
            is UiState.Idle -> {}
            is UiState.Loading -> ProgressDialog()
            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    if (AppProviders.dentist != null) {
                        preferencesHelper.putDentist(AppProviders.dentist!!)
                        activity?.startActivity(Intent(context, DentistMainActivity::class.java))
                        activity?.finish()
                    }
                }
            }

            is UiState.Error -> {
                LaunchedEffect(Unit) {
                    val message =
                        if ((uiState as UiState.Error).t.message == Constant.USER_TYPE_ERROR)
                            context.getString(R.string.user_type_is_not_dentist)
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
}
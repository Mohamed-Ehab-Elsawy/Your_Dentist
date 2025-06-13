package com.nca.yourdentist.presentation.screens.common.auth.forget_password

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.EmailTextField
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    viewModel: ForgetPasswordViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.forget_password),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                iconTint = primaryLight,
                onIconClick = {
                    navController.popBackStack()
                }
            )
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

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.sendResetPasswordEmail()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                enabled = uiState !is UiState.Loading
            ) {
                Text(
                    text = stringResource(R.string.reset_password),
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
                Toast.makeText(
                    context,
                    context.getString(R.string.password_reset_email_sent),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }
        }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                val errorMessage = (uiState as UiState.Error).t.localizedMessage
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
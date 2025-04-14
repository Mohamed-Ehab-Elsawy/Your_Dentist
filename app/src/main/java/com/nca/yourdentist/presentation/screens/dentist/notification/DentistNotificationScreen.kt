package com.nca.yourdentist.presentation.screens.dentist.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun DentistNotificationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.notification))
        }) { paddingValues ->
        Box(
            modifier = Modifier
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
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_no_notifications),
                    contentDescription = stringResource(
                        R.string.no_notifications_image
                    )
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.no_notifications_yet),
                    style = AppTypography.titleLarge,
                    color = primaryLight
                )
            }
        }
    }
}
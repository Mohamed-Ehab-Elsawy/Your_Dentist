package com.nca.yourdentist.presentation.screens.common.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import kotlinx.coroutines.delay
import org.koin.compose.getKoin

@Composable
fun SplashScreen(
    navController: NavController,
    preferencesHelper: PreferencesHelper = getKoin().get()
) {
    var isVisible by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing), label = ""
    )

    LaunchedEffect(Unit) {
        isVisible = true
        val patient: Patient? = preferencesHelper.fetchPatient()
        val dentist: Dentist? = preferencesHelper.fetchDentist()
        delay(2000)

        when {
            patient?.id != null -> navController.navigate(Screen.PatientHome.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }

            dentist?.id != null -> navController.navigate(Screen.DentistHome.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }

            !preferencesHelper.getBoolean(PreferencesHelper.SHOW_INTRO) ->
                navController.navigate(Screen.SelectUserType.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }

            else -> navController.navigate(Screen.Intro.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier
                .fillMaxSize(0.25f)
                .scale(scaleAnim)
                .alpha(alphaAnim)
        )
    }
}

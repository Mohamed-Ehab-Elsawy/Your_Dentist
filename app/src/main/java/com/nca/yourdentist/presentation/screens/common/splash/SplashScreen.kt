package com.nca.yourdentist.presentation.screens.common.splash

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.navigation.MainScreens
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.dentist.DentistMainActivity
import com.nca.yourdentist.presentation.screens.patient.PatientMainActivity
import com.nca.yourdentist.utils.AppProviders
import kotlinx.coroutines.delay
import org.koin.compose.getKoin

@Composable
fun SplashScreen(
    navController: NavController,
    preferencesHelper: PreferencesHelper = getKoin().get()
) {
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? Activity

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
            patient?.id != null -> {
                AppProviders.patient = patient
                activity?.startActivity(Intent(context, PatientMainActivity::class.java))
                activity?.finish()
            }

            dentist?.id != null -> {
                AppProviders.dentist = dentist
                activity?.startActivity(Intent(context, DentistMainActivity::class.java))
                activity?.finish()
            }

            !preferencesHelper.fetchBoolean(PreferencesHelper.SHOW_INTRO) ->
                navController.navigate(MainScreens.SelectUserType.route) {
                    popUpTo(MainScreens.Splash.route) { inclusive = true }
                }

            else -> navController.navigate(MainScreens.Intro.route) {
                popUpTo(MainScreens.Splash.route) { inclusive = true }
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
                .fillMaxSize(0.4f)
                .scale(scaleAnim)
                .alpha(alphaAnim)
        )
    }
}
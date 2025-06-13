package com.nca.yourdentist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.navigation.PatientNavGraph
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.customized.NoInternetDialog
import com.nca.yourdentist.presentation.component.ui.theme.AppTheme
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.transparent
import com.nca.yourdentist.presentation.component.ui.theme.white
import com.nca.yourdentist.presentation.utils.BottomNavItem
import com.nca.yourdentist.utils.updateBaseContextLocale
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import java.util.Locale

class PatientActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val preferencesHelper: PreferencesHelper by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestNotificationPermission()

        setContent {
            KoinAndroidContext {
                val navController = rememberNavController()
                var isConnected by remember { mutableStateOf(true) }

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                val bottomNavItems = listOf(
                    BottomNavItem(
                        name = stringResource(R.string.home),
                        route = PatientScreens.Home.route,
                        unselectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_home_line),
                        selectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_home_fill)
                    ),
                    BottomNavItem(
                        name = stringResource(R.string.reports),
                        route = PatientScreens.Reports.route,
                        unselectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_reports_history_line),
                        selectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_reports_fill)
                    ),
                    BottomNavItem(
                        name = stringResource(R.string.notifications),
                        route = PatientScreens.Notification.route,
                        unselectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_notification_line),
                        selectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_notification_fill)
                    ), BottomNavItem(
                        name = stringResource(R.string.settings),
                        route = PatientScreens.Settings.route,
                        unselectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_settings_line),
                        selectedIcon = ImageVector.Companion.vectorResource(R.drawable.ic_settings_fill)
                    )
                )
                selectedItemIndex = bottomNavItems.indexOfFirst { it.route == currentDestination }

                LaunchedEffect(Unit) {
                    networkMonitor.isConnected.collectLatest { isConnected = it }
                }

                AppTheme {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(containerColor = white, contentColor = primaryLight) {
                                bottomNavItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.route) {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                            }
                                        },
                                        label = { Text(text = item.name) },
                                        icon = {
                                            BadgedBox(badge = {
                                                if (item.badgeEnabled) {
                                                    Badge {}
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = if (index == selectedItemIndex) item.selectedIcon
                                                    else item.unselectedIcon,
                                                    contentDescription = item.name,
                                                    tint = primaryLight
                                                )
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = primaryLight,
                                            unselectedIconColor = primaryLight.copy(alpha = 0.6f),
                                            selectedTextColor = primaryLight,
                                            unselectedTextColor = primaryLight.copy(alpha = 0.6f),
                                            indicatorColor = transparent // removes blue indicator
                                        )
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            PatientNavGraph(navController = navController)
                        }
                    }
                    if (!isConnected)
                        NoInternetDialog()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale(getSavedLanguage())
        val context = newBase.updateBaseContextLocale(locale)
        super.attachBaseContext(context)
    }

    private fun getSavedLanguage(): String {
        return preferencesHelper.fetchString(PreferencesHelper.Companion.CURRENT_LANGUAGE)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}
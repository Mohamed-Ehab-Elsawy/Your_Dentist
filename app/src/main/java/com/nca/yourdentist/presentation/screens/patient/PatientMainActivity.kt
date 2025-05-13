package com.nca.yourdentist.presentation.screens.patient

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.navigation.PatientNavGraph
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.NoInternetDialog
import com.nca.yourdentist.presentation.component.ui.theme.MyAppTheme
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.utils.BottomNavItem
import com.nca.yourdentist.utils.updateBaseContextLocale
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import java.util.Locale

class PatientMainActivity : AppCompatActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val preferencesHelper: PreferencesHelper by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            val navController = rememberNavController()
            var isConnected by remember { mutableStateOf(true) }

            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val currentDestination =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val bottomNavItems = listOf(
                BottomNavItem(
                    name = stringResource(R.string.home),
                    route = PatientScreens.Home.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_home_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_home_fill)
                ),
                BottomNavItem(
                    name = stringResource(R.string.records),
                    route = PatientScreens.Records.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_reports_history_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_reports_fill)
                ),
                BottomNavItem(
                    name = stringResource(R.string.notifications),
                    route = PatientScreens.Notification.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_notification_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_notification_fill)
                ), BottomNavItem(
                    name = stringResource(R.string.settings),
                    route = PatientScreens.Settings.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_settings_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_settings_fill)
                )
            )
            selectedItemIndex = bottomNavItems.indexOfFirst { it.route == currentDestination }

            LaunchedEffect(Unit) {
                networkMonitor.isConnected.collectLatest { isConnected = it }
            }

            MyAppTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
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
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != 0) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.badgeEnabled) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) item.selectedIcon
                                                else item.unselectedIcon,
                                                contentDescription = item.name,
                                                tint = primaryLight
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        PatientNavGraph(navController = navController)
                    }
                }
                if (!isConnected)
                    NoInternetDialog()
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale(getSavedLanguage())
        val context = newBase.updateBaseContextLocale(locale)
        super.attachBaseContext(context)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1001 && grantResults.isNotEmpty()) {
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            preferencesHelper.putBoolean(PreferencesHelper.NOTIFICATION_ENABLED, isGranted)
            if (!isGranted) {
                Toast.makeText(this, "Notifications and Reminders are disabled", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getSavedLanguage(): String {
        return preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
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
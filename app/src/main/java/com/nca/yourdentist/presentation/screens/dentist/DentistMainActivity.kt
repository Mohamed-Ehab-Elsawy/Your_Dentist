package com.nca.yourdentist.presentation.screens.dentist

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.navigation.DentistScreens
import com.nca.yourdentist.navigation.PatientNavGraph
import com.nca.yourdentist.presentation.component.ui.theme.MyAppTheme
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.utils.BottomNavItem
import com.nca.yourdentist.utils.updateBaseContextLocale
import org.koin.android.ext.android.inject
import java.util.Locale

class DentistMainActivity : AppCompatActivity() {

    private val networkMonitor: NetworkMonitor by inject()
    private val preferencesHelper: PreferencesHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomNavItems = listOf(
                BottomNavItem(
                    name = stringResource(R.string.home),
                    route = DentistScreens.Home.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_home_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_home_fill)
                ),
                BottomNavItem(
                    name = stringResource(R.string.notifications),
                    route = DentistScreens.Notifications.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_notification_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_notification_fill)
                ), BottomNavItem(
                    name = stringResource(R.string.settings),
                    route = DentistScreens.Settings.route,
                    unselectedIcon = ImageVector.vectorResource(R.drawable.ic_settings_line),
                    selectedIcon = ImageVector.vectorResource(R.drawable.ic_settings_fill)
                )
            )

            val navController = rememberNavController()
            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val currentDestination =
                navController.currentBackStackEntryAsState().value?.destination?.route
            selectedItemIndex = bottomNavItems.indexOfFirst { it.route == currentDestination }

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
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale(getSavedLanguage())
        val context = newBase.updateBaseContextLocale(locale)
        super.attachBaseContext(context)
    }

    private fun getSavedLanguage(): String {
        return preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
    }
}
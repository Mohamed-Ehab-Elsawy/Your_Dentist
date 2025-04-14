package com.nca.yourdentist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.navigation.AppNavGraph
import com.nca.yourdentist.navigation.MainScreens
import com.nca.yourdentist.presentation.component.ui.NoInternetDialog
import com.nca.yourdentist.presentation.component.ui.theme.MyAppTheme
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.updateBaseContextLocale
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val preferencesHelper: PreferencesHelper by inject()
    private var loggedOut = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loggedOut = intent.getBooleanExtra(Constant.AUTH_SCREEN, false)
        setContent {
            val navController = rememberNavController()
            var isConnected by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                networkMonitor.isConnected.collectLatest { isConnected = it }
            }

            MyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(navController = navController)

                    if (loggedOut)
                        navController.navigate(MainScreens.SelectUserType.route)

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
        return preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
    }
}
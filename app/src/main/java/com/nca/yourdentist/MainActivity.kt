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
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.navigation.AuthNavGraph
import com.nca.yourdentist.navigation.AuthScreens
import com.nca.yourdentist.presentation.component.ui.customized.NoInternetDialog
import com.nca.yourdentist.presentation.component.ui.theme.AppTheme
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.updateBaseContextLocale
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val preferencesHelper: PreferencesHelper by inject()
    private var loggedOut = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        loggedOut = intent.getBooleanExtra(Constant.AUTH_SCREEN, false)
        setContent {
            KoinAndroidContext {
                val navController = rememberNavController()
                var isConnected by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    networkMonitor.isConnected.collectLatest { isConnected = it }
                }

                AppTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        AuthNavGraph(navController = navController)

                        if (loggedOut)
                            navController.navigate(AuthScreens.SelectUserType.route)

                        if (!isConnected)
                            NoInternetDialog()
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
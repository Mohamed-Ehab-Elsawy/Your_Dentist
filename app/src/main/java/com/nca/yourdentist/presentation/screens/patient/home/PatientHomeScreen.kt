package com.nca.yourdentist.presentation.screens.patient.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientHomeScreen(
    navController: NavController,
    viewModel: PatientHomeViewModel = koinViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            DrawerSheet {
                navController.navigate(Screen.PatientSettings.route)
                scope.launch {
                    drawerState.close()
                }
            }
        }, drawerState = drawerState
    ) {
        Scaffold(topBar = {
            TopApplicationBar(
                title = "Home",
                iconRes = R.drawable.ic_menu,
                iconTint = primaryLight,
            ) {
                scope.launch {
                    drawerState.apply { if (isOpen) close() else open() }
                }
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {

            }
        }
    }
}

@Composable
fun DrawerSheet(modifier: Modifier = Modifier, onSettingsClick: () -> Unit) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxWidth(0.8F)
            .fillMaxHeight()
    ) {
        Column(
            modifier = modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(bottom = 10.dp)
                    .background(color = primaryLight)
                    .fillMaxWidth()
                    .padding(vertical = 55.dp)
            )
            DrawerSheetItem(
                title = stringResource(R.string.settings),
                icon = painterResource(R.drawable.ic_settings)
            ) {
                onSettingsClick.invoke()
            }
        }
    }
}

@Composable
fun DrawerSheetItem(title: String, icon: Painter, onItemClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable { onItemClick.invoke() }) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = icon,
            contentDescription = null,
            tint = primaryLight,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            color = primaryLight,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

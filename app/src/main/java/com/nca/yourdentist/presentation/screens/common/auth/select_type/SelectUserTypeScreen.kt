package com.nca.yourdentist.presentation.screens.common.auth.select_type

import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.theme.AppTheme

@Composable
fun SelectUserTypeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Patient Selection
        UserTypeCard(
            title = stringResource(id = R.string.login_as_patient),
            imageRes = R.drawable.img_patient,
            onClick = { navController.navigate(Screen.PatientLogin.route) },
            modifier = Modifier.weight(1f) // Distribute space equally
        )

        // Dentist Selection
        UserTypeCard(
            title = stringResource(id = R.string.login_as_dentist),
            imageRes = R.drawable.img_dentist,
            onClick = { navController.navigate(Screen.DentistLogin.route) },
            modifier = Modifier.weight(1f) // Distribute space equally
        )
    }
}

@Composable
fun UserTypeCard(title: String, imageRes: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.6f)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

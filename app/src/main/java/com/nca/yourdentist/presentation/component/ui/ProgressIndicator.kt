package com.nca.yourdentist.presentation.component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Card(
            modifier = modifier
                .width(150.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            border = BorderStroke(2.dp, primaryLight)
        ) {
            Column(
                modifier = modifier
                    .background(Color.White.copy(alpha = 0.8f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = primaryLight)

                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.loading),
                    fontSize = 18.sp,
                    color = primaryLight
                )
            }
        }
    }
}
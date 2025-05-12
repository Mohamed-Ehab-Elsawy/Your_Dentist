package com.nca.yourdentist.presentation.screens.patient.notification.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.black
import com.nca.yourdentist.presentation.component.ui.theme.onSecondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceDimLightMediumContrast
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight

@Composable
fun NotificationItem(modifier: Modifier = Modifier, appNotification: AppNotification) {
    var isExpanded by remember { mutableStateOf(false) }
    var isRead by remember { mutableStateOf(appNotification.isRead) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = stringResource(R.string.arrow_rotation)
    )

    Card(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!appNotification.isRead) surfaceLight else surfaceDimLightMediumContrast
        )
    ) {
        Box(
            modifier = modifier
                .animateContentSize()
                .clickable {
                    isExpanded = !isExpanded
                    isRead = true
                }
        ) {

            Row(
                modifier = modifier
                    .wrapContentSize()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_notification_fill),
                    modifier = modifier
                        .padding(8.dp)
                        .size(54.dp),
                    contentDescription = null,
                    tint = if (!appNotification.isRead) primaryLight else onSecondaryLight
                )
                Column(
                    modifier = modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = appNotification.title,
                        style = AppTypography.titleMedium,
                        modifier = modifier.fillMaxWidth(),
                        color = if (!appNotification.isRead) black else secondaryLight
                    )

                    Text(
                        text = appNotification.body,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        modifier = modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth(),
                        style = AppTypography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = if (!appNotification.isRead) black else secondaryLight
                    )
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_down_arrow),
                contentDescription = stringResource(R.string.expand_notification_arrow),
                modifier = Modifier
                    .padding(bottom = 4.dp, end = 8.dp)
                    .align(Alignment.BottomEnd)
                    .rotate(rotationAngle),
                tint = if (!appNotification.isRead) primaryLight else onSecondaryLight
            )
        }
    }
}
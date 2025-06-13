package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.outlineVariantLight
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DentistProfileImage(modifier: Modifier = Modifier, dentistProfileUri: String?) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    SubcomposeAsyncImage(
        model = dentistProfileUri,
        contentDescription = stringResource(R.string.dentist_profile_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape),
        loading = {
            Box(
                modifier = Modifier
                    .shimmer(shimmerInstance)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(outlineVariantLight)
            )
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.img_dentist_test),
                contentDescription = stringResource(R.string.dentist_placeholder_image),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    )
}
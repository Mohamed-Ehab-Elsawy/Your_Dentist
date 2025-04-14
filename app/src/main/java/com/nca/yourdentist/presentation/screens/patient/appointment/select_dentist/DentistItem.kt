package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.nca.yourdentist.R
import com.nca.yourdentist.data.model.CityArea.Companion.cairoAreasMap
import com.nca.yourdentist.data.model.CityArea.Companion.citiesMap
import com.nca.yourdentist.data.model.CityArea.Companion.gizaAreasMap
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.onSecondaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.outlineVariantLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DentistProfileImage(dentistProfileUri: String?) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    SubcomposeAsyncImage(
        model = dentistProfileUri,
        contentDescription = stringResource(R.string.dentist_profile_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
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

@Composable
fun DentistItem(dentist: Dentist, onBookNowClick: (Dentist) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceLight),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DentistProfileImage(dentist.profileImage)

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Dentist Name
                    Text(
                        text = dentist.name!!,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryLight
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Location (City - Area)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = citiesMap[dentist.city]?.nameEn ?: "Egypt",
                            fontSize = 16.sp,
                            color = onSecondaryContainerLight
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "-",
                            fontSize = 16.sp,
                            color = onSecondaryContainerLight
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (dentist.city == 1)
                                cairoAreasMap[dentist.area]?.nameEn ?: ""
                            else if (dentist.city == 2)
                                gizaAreasMap[dentist.area]?.nameEn ?: ""
                            else "",
                            fontSize = 16.sp,
                            color = onSecondaryContainerLight
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = dentist.rate.toString(),
                            fontSize = 18.sp,
                            color = onSecondaryContainerLight
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_rate),
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
        // Book Now Button
        CustomButton(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            onClick = {
                Log.d("BookNowClick", "Clicked on ${dentist.name}")
                onBookNowClick.invoke(dentist)
            },
            text = stringResource(R.string.book_now),
            enabled = true
        )
    }
}
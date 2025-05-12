package com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.CityArea.Companion.cairoAreasMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.citiesMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.gizaAreasMap
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.presentation.component.ui.theme.onSecondaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight
import com.nca.yourdentist.utils.LanguageConstants
import com.nca.yourdentist.utils.localizedName

@Composable
fun DentistItem(
    dentist: Dentist,
    activeLanguage: String,
    onBookNowClick: (Dentist) -> Unit
) {
    val context = LocalContext.current
    val cityIndex = dentist.clinic?.city
    val areaIndex = dentist.clinic?.area

    LaunchedEffect(Unit) {
        Log.e("DentistItem", "city : $cityIndex , area : $areaIndex")
    }
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
                DentistProfileImage(dentistProfileUri = dentist.profileImage)

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
                            text = citiesMap[cityIndex]?.localizedName(context) ?: "",
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
                            text =
                                if (cityIndex == 1)
                                    cairoAreasMap[areaIndex]?.localizedName(context) ?: "غير محدد"
                                else if (cityIndex == 2)
                                    gizaAreasMap[areaIndex]?.localizedName(context) ?: "غير محدد"
                                else
                                    "",
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

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text =
                            if (activeLanguage == LanguageConstants.ENGLISH)
                                dentist.sessionPrice.toString() + " EGP"
                            else if (activeLanguage == LanguageConstants.ARABIC)
                                dentist.sessionPrice.toString() + " ج.م"
                            else
                                "",
                        fontSize = 16.sp,
                        color = onSecondaryContainerLight
                    )

                }
            }
            BookNowButton {
                onBookNowClick.invoke(dentist)
            }
        }
    }
}
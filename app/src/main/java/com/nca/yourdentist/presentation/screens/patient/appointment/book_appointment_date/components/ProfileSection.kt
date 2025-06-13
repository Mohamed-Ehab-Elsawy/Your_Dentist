package com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component.DentistProfileImage
import com.nca.yourdentist.utils.getExperienceText

@Composable
fun ProfileSection(dentist: Dentist) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(vertical = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DentistProfileImage(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    dentistProfileUri = dentist.profileImage
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    //Dentist name
                    Text(
                        "Prof. Dr.",
                        style = AppTypography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryLight
                    )
                    Text(
                        dentist.name ?: "",
                        style = AppTypography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryLight
                    )

                    // Session price
                    Text(
                        dentist.sessionPrice.toString() + "EGP",
                        color = secondaryLight,
                        style = AppTypography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = dentist.createdAt.getExperienceText(),
                        style = AppTypography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        stringResource(R.string.experience),
                        style = AppTypography.bodySmall,
                        color = secondaryLight
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = dentist.patientsNumber,
                        style = AppTypography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(R.string.patients),
                        style = AppTypography.bodySmall,
                        color = secondaryLight
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star_rate),
                            contentDescription = stringResource(R.string.rate_star_icon),
                            tint = Color(0xFFFFC107)
                        )
                        Text(
                            text = dentist.rate.toString(),
                            style = AppTypography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = stringResource(R.string.reviews),
                        style = AppTypography.bodySmall,
                        color = secondaryLight
                    )
                }
            }
        }
    }
}
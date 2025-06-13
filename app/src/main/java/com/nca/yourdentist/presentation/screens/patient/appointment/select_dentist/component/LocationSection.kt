package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.CityArea.Companion.cairoAreasMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.citiesMap
import com.nca.yourdentist.data.models.users.CityArea.Companion.gizaAreasMap
import com.nca.yourdentist.presentation.component.ui.customized.CustomMapDropDownMenu
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight


@Composable
fun LocationSection(
    modifier: Modifier = Modifier,
    selectedCityKey: Int,
    selectedAreaKey: Int,
    onCityChange: (Int) -> Unit,
    onAreaChange: (Int) -> Unit
) {
    Text(
        text = stringResource(id = R.string.choose_your_current_city_and_area),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = primaryLight,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
    )

    Column(
        modifier = modifier.fillMaxWidth(),

        ) {
        CustomMapDropDownMenu(
            options = citiesMap,
            selectedKey = selectedCityKey,
            label = stringResource(R.string.city),
            onValueChange = { onCityChange.invoke(it) }
        )

        Spacer(Modifier.height(4.dp))

        CustomMapDropDownMenu(
            options =
                if (selectedCityKey == 1) cairoAreasMap
                else if (selectedCityKey == 2) gizaAreasMap
                else emptyMap(),
            selectedKey = selectedAreaKey,
            label = stringResource(R.string.area),
            onValueChange = { onAreaChange.invoke(it) }
        )
    }
}
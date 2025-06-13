package com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton

@Composable
fun NotesSection(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange.invoke(it) },
            label = { Text(text = stringResource(R.string.notes)) },
            modifier = modifier
                .height(200.dp)
                .fillMaxWidth(),
            singleLine = false,
            textStyle = LocalTextStyle.current.copy(
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp
            )
        )
        Spacer(Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.submit),
            onClick = {
                onSubmitClick.invoke()
            }
        )
        Spacer(Modifier.height(8.dp))
    }
}
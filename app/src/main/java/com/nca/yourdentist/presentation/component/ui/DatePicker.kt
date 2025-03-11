package com.nca.yourdentist.presentation.component.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.errorLight


@Composable
fun BirthdatePicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null
) {
    val context = LocalContext.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                openDatePicker(context, onValueChange)
            },
        label = { Text(stringResource(id = R.string.birthdate)) },
        trailingIcon = {
            IconButton(onClick = {
                openDatePicker(context, onValueChange)
            }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Pick Date")
            }
        },
        supportingText = {
            if (!errorMessage.isNullOrEmpty())
                Text(errorMessage, color = errorLight)
        },
        readOnly = true,
        value = value,
        onValueChange = {}
    )
}

private fun openDatePicker(context: Context, onValueChange: (String) -> Unit) {
    val datePicker = DatePickerDialog(context, { _, year, month, dayOfMonth ->
        onValueChange.invoke("$dayOfMonth/${month + 1}/$year")
    }, 2000, 0, 1)
    datePicker.show()
}
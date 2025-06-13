package com.nca.yourdentist.presentation.screens.common.auth.patient_signup.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.errorLight

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        maxLines = 1,
        label = { Text(stringResource(id = R.string.full_name)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        isError = isError,
        supportingText = {
            Text(errorText, color = errorLight)
        }
    )
}
package com.nca.yourdentist.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.presentation.component.ui.theme.errorLight

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        isError = errorMessage != null,
        supportingText = {
            errorMessage?.let {
                Text(
                    it,
                    color = errorLight
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    )
}
package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.errorLight

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier, value: String,
    onValueChange: (String) -> Unit, errorMessage: String? = null
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value, maxLines = 1, onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = AppTypography.bodyLarge.fontSize),
        label = { Text(text = stringResource(R.string.e_mail), style = AppTypography.bodyLarge) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        isError = errorMessage != null,
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) Text(errorMessage, color = errorLight)
        }
    )
}
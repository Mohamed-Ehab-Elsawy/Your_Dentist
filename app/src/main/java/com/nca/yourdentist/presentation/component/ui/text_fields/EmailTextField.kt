package com.nca.yourdentist.presentation.component.ui.text_fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value, maxLines = 1,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = primaryLight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        label = {
            Text(
                text = stringResource(R.string.e_mail),
                style = MaterialTheme.typography.bodyLarge, color = primaryLight
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        isError = errorMessage != null,
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) { // Only show when error exists
                Text(
                    errorMessage,
                    color = errorLight
                )
            }
        }
    )
}
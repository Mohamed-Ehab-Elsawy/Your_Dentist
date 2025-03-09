package com.nca.yourdentist.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.password),
    errorMessage: String? = null,
    modifier: Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = primaryLight, fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyLarge, color = primaryLight)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector =
                        if (isPasswordVisible)
                            ImageVector.vectorResource(R.drawable.ic_closed_eye)
                        else
                            ImageVector.vectorResource(R.drawable.ic_eye),
                    contentDescription =
                        if (isPasswordVisible) "Hide Password" else "Show Password"
                )
            }
        },
        isError = errorMessage != null,
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) { // Only show when error exists
                Text(
                    errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

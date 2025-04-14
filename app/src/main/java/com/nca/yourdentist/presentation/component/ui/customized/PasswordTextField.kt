package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.errorLight

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier, value: String,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.password), errorMessage: String? = null
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        maxLines = 1,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = AppTypography.bodyLarge.fontSize),
        label = { Text(text = label, style = AppTypography.bodyLarge) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector =
                        if (isPasswordVisible)
                            ImageVector.vectorResource(R.drawable.ic_eye)
                        else
                            ImageVector.vectorResource(R.drawable.ic_closed_eye),
                    contentDescription =
                        if (isPasswordVisible) "Hide Password" else "Show Password"
                )
            }
        },
        isError = errorMessage != null,
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) Text(errorMessage, color = errorLight)
        }
    )
}
package com.nca.yourdentist.presentation.component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdownList(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null
) {
    val options = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female))
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
            readOnly = true,
            value = value,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.gender)) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                }
            }, supportingText = {
                if (!errorMessage.isNullOrEmpty()) { // Only show when error exists
                    Text(
                        errorMessage,
                        color = errorLight
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = onPrimaryLight),
        ) {
            options.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender, color = primaryLight) },
                    onClick = {
                        onValueChange.invoke(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

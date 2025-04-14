package com.nca.yourdentist.presentation.component.ui.customized

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
import com.nca.yourdentist.data.model.CityArea
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomStringDropDownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null
) {
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
            label = { Text(text = label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                }
            }, supportingText = {
                if (!errorMessage.isNullOrEmpty()) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMapDropDownMenu(
    modifier: Modifier = Modifier,
    options: Map<Int, CityArea>,
    selectedKey: Int?,
    label: String,
    onValueChange: (Int) -> Unit
) {
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
            value = options[selectedKey]?.nameEn ?: "", // Show the selected Arabic text
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(R.string.dropdown)
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = onPrimaryLight),
        ) {
            options.forEach { (key, value) ->
                DropdownMenuItem(
                    text = { Text(value.nameEn, color = primaryLight) },
                    onClick = {
                        onValueChange.invoke(key) // Store Int value
                        expanded = false
                    }
                )
            }
        }
    }
}
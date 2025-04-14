package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String, onClick: () -> Unit, enabled: Boolean = true
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
        enabled = enabled
    ) {
        Text(
            text = text,
            color = white,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
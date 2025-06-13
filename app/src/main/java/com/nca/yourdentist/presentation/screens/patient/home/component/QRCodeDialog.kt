package com.nca.yourdentist.presentation.screens.patient.home.component

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.outlineVariantLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.utils.Provider
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun QRCodeDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.your_qr_code),
                    color = primaryLight,
                    style = AppTypography.headlineLarge
                )
            }
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (Provider.patientQRCodeBitmap != null) {
                    Image(
                        bitmap = Provider.patientQRCodeBitmap!!.asImageBitmap(),
                        contentDescription = stringResource(R.string.qr_code_preview),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                } else {
                    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
                    SubcomposeAsyncImage(
                        model = Provider.patient!!.qrCode,
                        contentDescription = stringResource(R.string.your_qr_code),
                        loading = {
                            Box(
                                modifier = Modifier
                                    .shimmer(shimmerInstance)
                                    .size(100.dp)
                                    .background(outlineVariantLight)
                            )
                        }, success = {
                            val drawable = (it.result).drawable
                            if (drawable is BitmapDrawable) {
                                Image(
                                    bitmap = drawable.bitmap.asImageBitmap(),
                                    contentDescription = stringResource(R.string.qr_code_preview),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(200.dp)
                                )
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    onClick = { onDismiss.invoke() },
                    text = stringResource(R.string.close)
                )
            }
        }
    )
}
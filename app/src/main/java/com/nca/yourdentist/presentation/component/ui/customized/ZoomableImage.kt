package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.outlineVariantLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import java.io.File

@Composable
fun ZoomableImage(
    imgURL: File? = null, onClose: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
        offset = offset + panChange
    }

    Dialog(onDismissRequest = { onClose() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { }, // Tap to close
                        onDoubleTap = {
                            scale = if (scale > 1f) 1f else 2f // Toggle zoom
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = imgURL,
                contentDescription = stringResource(R.string.x_ray_image),
                loading = {
                    Box(
                        modifier = Modifier
                            .shimmer(shimmerInstance)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(outlineVariantLight)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(transformableState),
                contentScale = ContentScale.FillBounds
            )
            IconButton(
                onClick = { onClose() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    tint = white,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
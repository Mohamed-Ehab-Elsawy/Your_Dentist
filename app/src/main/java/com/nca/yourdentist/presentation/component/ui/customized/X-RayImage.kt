package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.outlineVariantLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import java.io.File

@Composable
fun `X-RayImage`(
    modifier: Modifier = Modifier,
    title: String,
    imageURL: String? = null,
    imageFile: File? = null,
    onZoomIconClick: () -> Unit
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = primaryLight,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            SubcomposeAsyncImage(
                model = imageURL,
                contentDescription = stringResource(R.string.result_image),
                loading = {
                    Box(
                        modifier = Modifier
                            .shimmer(shimmerInstance)
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(outlineVariantLight)
                    )
                },
                error = {
                    if (imageFile != null)
                        AsyncImage(
                            model = imageFile.toUri(),
                            contentDescription = stringResource(R.string.result_image),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    else
                        Box(
                            modifier = Modifier
                                .shimmer(shimmerInstance)
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(outlineVariantLight)
                        )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds
            )
            ZoomIcon(
                modifier = Modifier.align(Alignment.TopEnd),
                onZoomIconClick = onZoomIconClick
            )
        }
    }
}
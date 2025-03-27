package com.example.stylefeed.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.stylefeed.R
import com.example.stylefeed.domain.model.Style

@Composable
fun StyleCard(style: Style, modifier: Modifier = Modifier, imageAspectRatio: Float,recentlyAdded: Boolean,) {

    // ✅ 애니메이션 상태 정의
    val animatedAlpha = remember { Animatable(1f) }

    LaunchedEffect(recentlyAdded) {
        if (recentlyAdded) {
            animatedAlpha.snapTo(0f)
            animatedAlpha.animateTo(1f, animationSpec = tween(durationMillis = 800))
        } else {
            animatedAlpha.snapTo(1f)
        }
    }


    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .graphicsLayer { alpha = animatedAlpha.value }
            .clickable { /* 상품 클릭 */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = style.thumbnailUrl,
            contentDescription = style.linkUrl,
            modifier = Modifier
                .aspectRatio(imageAspectRatio) // ✅ 항상 aspectRatio 사용
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        )

        Spacer(modifier = Modifier.height(6.dp))


    }
}

@Preview(showBackground = true, widthDp = 150)
@Composable
fun StyleCardPreview() {
    StyleCard(
        style = Style(
            linkUrl = "https://example.com",
            thumbnailUrl = ""
        ),
        imageAspectRatio = 1f,
        recentlyAdded = true
    )
}
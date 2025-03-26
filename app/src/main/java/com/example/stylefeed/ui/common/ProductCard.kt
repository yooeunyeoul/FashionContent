package com.example.stylefeed.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    recentlyAdded: Boolean,
    imageAspectRatio: Float
) {

    // ✅ 애니메이션 상태 정의
    val animatedAlpha = remember { Animatable(1f) }

    LaunchedEffect(recentlyAdded) {
        if (recentlyAdded) {
            animatedAlpha.snapTo(0f)
            animatedAlpha.animateTo(1f, animationSpec = tween(durationMillis = 900))
        } else {
            animatedAlpha.snapTo(1f)
        }
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .graphicsLayer { alpha = animatedAlpha.value }
            .clickable { /* 상품 클릭 */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = product.thumbnailUrl,
            contentDescription = product.brandName,
            modifier = Modifier
                .aspectRatio(imageAspectRatio) // ✅ 항상 aspectRatio 사용
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .heightIn(min = 80.dp, max = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = product.brandName,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${product.formattedPrice}원",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )

            if (product.saleRate > 0) {
                Text(
                    text = "${product.saleRate}% 할인",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .background(
                        color = if (product.hasCoupon) Color(0xFFFEF2E9) else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (product.hasCoupon) "쿠폰" else " ",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (product.hasCoupon) Color(0xFFF76A24) else Color.Transparent
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

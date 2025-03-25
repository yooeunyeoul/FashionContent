package com.example.stylefeed.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stylefeed.domain.model.Style

@Composable
fun StyleCard(style: Style, modifier: Modifier = Modifier, imageAspectRatio: Float = 500f / 700f) {


    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
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
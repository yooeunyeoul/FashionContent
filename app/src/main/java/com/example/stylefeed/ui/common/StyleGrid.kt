package com.example.stylefeed.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.stylefeed.domain.model.Style

@Composable
fun StyleGrid(styles: List<Style>) {
    val itemHeight = 120.dp  // 각 아이템 높이 (원하는 대로 조정 가능)
    val spacing = 8.dp
    val totalRows = ((styles.size - 1) / 3) + 2 // 첫 아이템이 2줄 차지하므로 +2

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        userScrollEnabled = false, // 내부 스크롤 비활성화 (필수!)
        modifier = Modifier
            .padding(8.dp)
            .height((itemHeight + spacing) * totalRows)  // 전체 높이 명시적 지정
    ) {
        itemsIndexed(
            items = styles,
            span = { index, _ ->
                if (index == 0) GridItemSpan(2) else GridItemSpan(1)
            }
        ) { index, style ->
            val aspectRatio = 1f
            val heightModifier = if (index == 0) {
                Modifier
                    .aspectRatio(aspectRatio)
                    .padding(spacing)
            } else {
                Modifier
                    .aspectRatio(aspectRatio)
                    .padding(spacing)
            }

            StyleCard(style, modifier = heightModifier)
        }
    }
}
@Composable
fun StyleCard(style: Style, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { /* 스타일 클릭 처리 */ }
    ) {
        Image(
            painter = rememberAsyncImagePainter(style.thumbnailUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
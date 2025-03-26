package com.example.stylefeed.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Product

private val GridSpacing = 4.dp
private val TextHeight = 80.dp
private val ExtraPadding = 20.dp // 카드 내부 추가 패딩(이미지와 텍스트 사이 간격 등)

fun calculateGridRows(itemCount: Int, columns: Int) = (itemCount + columns - 1) / columns


@Composable
fun ProductGrid(products: List<Product>, recentlyAddedIds: Set<String>, imageAspectRatio: Float) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val columns = 3
    val totalSpacing = GridSpacing * (columns - 1)
    val itemSize = (screenWidthDp - totalSpacing) / columns
    val imageHeight = itemSize / imageAspectRatio
    val itemHeight = imageHeight + TextHeight + ExtraPadding
    val rows = calculateGridRows(products.size, columns)
    val gridHeight = itemHeight * rows + GridSpacing * (rows - 1)













    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight), // ✅ 계산된 높이 사용
        verticalArrangement = Arrangement.spacedBy(GridSpacing),
        horizontalArrangement = Arrangement.spacedBy(GridSpacing),
        userScrollEnabled = false
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                recentlyAdded = recentlyAddedIds.contains(product.linkUrl),
                modifier = Modifier.width(itemSize),
                imageAspectRatio = imageAspectRatio
            )
        }
    }
}
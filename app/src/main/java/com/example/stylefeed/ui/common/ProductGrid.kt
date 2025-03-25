package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductGrid(products: List<Product>) {
    // 화면의 너비를 기준으로 그리드 아이템 높이를 설정 (가로:세로 = 1:1)
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val spacing = 4.dp // 아이템 간의 간격 조정
    val totalSpacing = spacing * 2 // 3개의 열은 간격이 2번 존재
    val itemSize = (screenWidthDp - totalSpacing) / 3

    val rows = (products.size + 2) / 3
    val gridHeight = itemSize * rows + spacing * (rows - 1)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        userScrollEnabled = false
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                modifier = Modifier
                    .size(itemSize), imageAspectRatio = 1f
            )
        }
    }
}
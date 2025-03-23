package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductGrid(products: List<Product>) {
    val displayProducts = products.take(6)

    // Grid의 전체 높이를 컨텐츠 크기에 맞게 자동으로 결정해주기 위한 방법
    val rows = (displayProducts.size + 2) / 3
    val itemHeight = 200.dp  // 카드의 예상 최대 높이
    val gridHeight = itemHeight * rows

    //320 x320

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight), // 명확한 최대 높이 설정
        userScrollEnabled = false // Grid 자체 스크롤 비활성화 (외부 LazyColumn이 스크롤 관리)
    ) {
        items(displayProducts) { product ->
            ProductCard(product)
        }
    }
}
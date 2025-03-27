package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductHorizontalList(products: List<Product>, recentlyAddedIds: Set<String>,imageAspectRatio: Float) {
    val cardHeight = 300.dp  // 🔥 전체 카드 높이를 확실히 줄임
    val imageHeight = 200.dp  // 이미지 높이 설정

    val cardWidth = imageHeight * imageAspectRatio  // 이미지 비율을 기준으로 너비 설정

    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
    ) {
        items(products) { product ->
            ProductCard(
                modifier = Modifier
                    .width(cardWidth)
                    .fillMaxHeight(),
                product = product,
                imageAspectRatio = imageAspectRatio,
                recentlyAdded = recentlyAddedIds.contains(product.linkUrl)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductHorizontalListPreview() {
    val sampleProducts = List(5) { index ->
        Product(
            linkUrl = "https://example.com/product$index",
            thumbnailUrl = "",
            brandName = "브랜드 $index",
            formattedPrice = "${10_000 + index * 1000}원",
            saleRate = 10 * index,
            hasCoupon = index % 2 == 0
        )
    }

    ProductHorizontalList(
        products = sampleProducts,
        recentlyAddedIds = setOf("https://example.com/product2", "https://example.com/product4"),
        imageAspectRatio = 1f
    )
}

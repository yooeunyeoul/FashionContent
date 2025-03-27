package com.example.stylefeed.ui.screens.product.components.grid

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylefeed.designsystem.components.grid.FixedVerticalGrid
import com.example.stylefeed.domain.model.Product
import com.example.stylefeed.ui.screens.product.components.cards.ProductCard

private val GridSpacing = 4.dp
private val TextHeight = 80.dp
private val ExtraPadding = 20.dp


@Composable
fun ProductGrid(
    products: List<Product>,
    recentlyAddedIds: Set<String>,
    imageAspectRatio: Float
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val columns = 3
    val totalSpacing = GridSpacing * (columns - 1)
    val itemSize = (screenWidthDp - totalSpacing) / columns
    val imageHeight = itemSize / imageAspectRatio
    val itemHeight = imageHeight + TextHeight + ExtraPadding

    FixedVerticalGrid(
        items = products,
        columns = columns,
        itemSpacing = GridSpacing,
        itemHeight = itemHeight
    ) { product, modifier ->
        ProductCard(
            product = product,
            recentlyAdded = recentlyAddedIds.contains(product.linkUrl),
            modifier = modifier.width(itemSize),
            imageAspectRatio = imageAspectRatio
        )
    }
}

@Preview(showBackground = true, name = "ProductGrid Preview")
@Composable
fun ProductGridPreview() {
    val sampleProducts = listOf(
        Product(
            linkUrl = "https://example.com/product/1",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "Nike",
            formattedPrice = "₩150,000",
            saleRate = 30,
            hasCoupon = true
        ),
        Product(
            linkUrl = "https://example.com/product/2",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "Adidas",
            formattedPrice = "₩120,000",
            saleRate = 0,
            hasCoupon = false
        ),
        Product(
            linkUrl = "https://example.com/product/3",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "New Balance",
            formattedPrice = "₩89,000",
            saleRate = 20,
            hasCoupon = true
        ),
        Product(
            linkUrl = "https://example.com/product/4",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "Puma",
            formattedPrice = "₩75,000",
            saleRate = 10,
            hasCoupon = false
        ),
        Product(
            linkUrl = "https://example.com/product/5",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "Fila",
            formattedPrice = "₩99,000",
            saleRate = 15,
            hasCoupon = true
        ),
        Product(
            linkUrl = "https://example.com/product/6",
            thumbnailUrl = "https://via.placeholder.com/150",
            brandName = "Reebok",
            formattedPrice = "₩80,000",
            saleRate = 25,
            hasCoupon = false
        )
    )

    ProductGrid(
        products = sampleProducts,
        recentlyAddedIds = setOf("https://example.com/product/1", "https://example.com/product/4"),
        imageAspectRatio = 1f
    )
}
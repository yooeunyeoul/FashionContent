package com.example.stylefeed.ui.common
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductHorizontalList(products: List<Product>) {
    LazyRow(modifier = Modifier.height(250.dp)) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}
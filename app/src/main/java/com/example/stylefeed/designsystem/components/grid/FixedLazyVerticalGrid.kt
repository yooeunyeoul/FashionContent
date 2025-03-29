package com.example.stylefeed.designsystem.components.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun <T> FixedVerticalGrid(
    items: List<T>,
    columns: Int,
    itemSpacing: Dp,
    itemHeight: Dp,
    userScrollEnabled: Boolean = false,
    keySelector: ((T) -> Any)? = null, // 🔥 추가!
    content: @Composable (item: T, modifier: Modifier) -> Unit
) {
    val rows = (items.size + columns - 1) / columns
    val totalHeight = itemHeight * rows + itemSpacing * (rows - 1)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxWidth()
            .height(totalHeight),
        verticalArrangement = Arrangement.spacedBy(itemSpacing),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        userScrollEnabled = userScrollEnabled
    ) {
        if (keySelector != null) {
            items(
                items = items,
                key = keySelector // ✅ 고유 key 적용
            ) { item ->
                content(item, Modifier)
            }
        } else {
            items(items) { item ->
                content(item, Modifier)
            }
        }
    }
}
@Preview(showBackground = true, name = "FixedVerticalGrid - 6 Items (2 Columns)")
@Composable
fun FixedVerticalGridPreview_6Items() {
    MaterialTheme {
        FixedVerticalGrid(
            items = List(6) { "Item ${it + 1}" },
            columns = 2,
            itemSpacing = 8.dp,
            itemHeight = 100.dp
        ) { item, modifier ->
            Box(
                modifier = modifier
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item)
            }
        }
    }
}

@Preview(showBackground = true, name = "FixedVerticalGrid - 9 Items (3 Columns)")
@Composable
fun FixedVerticalGridPreview_9Items() {
    MaterialTheme {
        FixedVerticalGrid(
            items = List(9) { "Item ${it + 1}" },
            columns = 3,
            itemSpacing = 4.dp,
            itemHeight = 80.dp
        ) { item, modifier ->
            Box(
                modifier = modifier
                    .background(Color.Cyan, RoundedCornerShape(4.dp))
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true, name = "FixedVerticalGrid - 7 Items (3 Columns)")
@Composable
fun FixedVerticalGridPreview_7Items() {
    MaterialTheme {
        FixedVerticalGrid(
            items = List(7) { "Item ${it + 1}" },
            columns = 3,
            itemSpacing = 6.dp,
            itemHeight = 60.dp
        ) { item, modifier ->
            Box(
                modifier = modifier
                    .background(Color.Magenta.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
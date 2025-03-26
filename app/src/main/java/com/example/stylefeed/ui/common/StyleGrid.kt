package com.example.stylefeed.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Style

@Composable
fun StyleGrid(styles: List<Style>, recentlyAddedIds: Set<String>) {
    val spacing = 8.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnCount = 3
    val totalSpacing = spacing * (columnCount - 1)
    val columnWidth = (screenWidth - totalSpacing - spacing * 2) / columnCount
    val bigItemHeight = columnWidth * 2 + spacing

    val gridItems = styles.drop(3)
    val gridRows = (gridItems.size + 2) / 3
    val gridHeight = columnWidth * gridRows + spacing * (gridRows - 1)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(bigItemHeight),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            // 첫 번째 큰 아이템 (2x2)
            StyleCard(
                style = styles[0],
                modifier = Modifier
                    .width(columnWidth * 2 + spacing)
                    .aspectRatio(1f), recentlyAdded = recentlyAddedIds.contains(styles[0].linkUrl)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier
                    .width(columnWidth)
                    .fillMaxHeight()
            ) {
                if (styles.size > 1) {
                    StyleCard(
                        style = styles[1],
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        recentlyAdded = recentlyAddedIds.contains(styles[1].linkUrl)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                if (styles.size > 2) {
                    StyleCard(
                        style = styles[2],
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        recentlyAdded = recentlyAddedIds.contains(styles[2].linkUrl)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        if (gridItems.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight)
                    .padding(top = spacing)
            ) {
                items(gridItems, key = { it.linkUrl }) { style ->
                    StyleCard(
                        style = style,
                        modifier = Modifier.aspectRatio(1f),
                        recentlyAdded = recentlyAddedIds.contains(style.linkUrl)
                    )
                }
            }
        }
    }
}
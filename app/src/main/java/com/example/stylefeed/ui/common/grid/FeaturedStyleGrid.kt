package com.example.stylefeed.ui.common.grid

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Style
import com.example.stylefeed.ui.common.StyleCard

@Composable
fun FeaturedStyleGrid(
    styles: List<Style>,
    recentlyAddedIds: Set<String>,
    imageAspectRatio: Float,
    spacing: Dp,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnCount = 3
    val totalSpacing = spacing * (columnCount - 1)
    val columnWidth = (screenWidth - totalSpacing - spacing * 2) / columnCount
    val bigItemHeight = columnWidth * 2 + spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(bigItemHeight),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        StyleCard(
            style = styles[0],
            modifier = Modifier
                .width(columnWidth * 2 + spacing)
                .aspectRatio(1f),
            recentlyAdded = recentlyAddedIds.contains(styles[0].linkUrl),
            imageAspectRatio = imageAspectRatio
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
                    recentlyAdded = recentlyAddedIds.contains(styles[1].linkUrl),
                    imageAspectRatio = imageAspectRatio
                )
            } else Spacer(modifier = Modifier.weight(1f))

            if (styles.size > 2) {
                StyleCard(
                    style = styles[2],
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    recentlyAdded = recentlyAddedIds.contains(styles[2].linkUrl),
                    imageAspectRatio = imageAspectRatio
                )
            } else Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeaturedStyleGridPreview() {
    val dummyStyles = listOf(
        Style(linkUrl = "https://example.com/style1", thumbnailUrl = "https://picsum.photos/400"),
        Style(linkUrl = "https://example.com/style2", thumbnailUrl = "https://picsum.photos/401"),
        Style(linkUrl = "https://example.com/style3", thumbnailUrl = "https://picsum.photos/402")
    )

    FeaturedStyleGrid(
        styles = dummyStyles,
        recentlyAddedIds = emptySet(),
        imageAspectRatio = 1f,
        spacing = 8.dp
    )
}
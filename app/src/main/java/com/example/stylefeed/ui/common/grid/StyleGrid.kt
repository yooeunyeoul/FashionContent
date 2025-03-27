package com.example.stylefeed.ui.common.grid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylefeed.R
import com.example.stylefeed.designsystem.components.grid.FixedVerticalGrid
import com.example.stylefeed.domain.model.Style
import com.example.stylefeed.ui.common.StyleCard

@Composable
fun StyleGrid(
    styles: List<Style>,
    recentlyAddedIds: Set<String>,
    imageAspectRatio: Float
) {
    val spacing = 8.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnCount = 3
    val totalSpacing = spacing * (columnCount - 1)
    val columnWidth = (screenWidth - totalSpacing - spacing * 2) / columnCount

    Column(
        modifier = Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally

    ) {
        if (styles.isNotEmpty()) {
            FeaturedStyleGrid(
                styles = styles,
                recentlyAddedIds = recentlyAddedIds,
                imageAspectRatio = imageAspectRatio,
                spacing = spacing
            )
        }

        val gridItems = styles.drop(3)

        if (gridItems.isNotEmpty()) {
            FixedVerticalGrid(
                items = gridItems,
                columns = 3,
                itemSpacing = spacing,
                itemHeight = columnWidth
            ) { style, modifier ->
                StyleCard(
                    style = style,
                    modifier = modifier.aspectRatio(1f),
                    recentlyAdded = recentlyAddedIds.contains(style.linkUrl),
                    imageAspectRatio = imageAspectRatio
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StyleGridPreview() {
    val context = LocalContext.current
    val localUri = "android.resource://${context.packageName}/${R.drawable.ic_launcher_foreground}"

    val sampleStyles = List(7) { index ->
        Style(
            linkUrl = "https://example.com/style$index",
            thumbnailUrl = localUri
        )
    }

    StyleGrid(
        styles = sampleStyles,
        recentlyAddedIds = setOf(sampleStyles[0].linkUrl, sampleStyles[3].linkUrl),
        imageAspectRatio = 1f
    )
}
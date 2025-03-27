@file:OptIn(ExperimentalFoundationApi::class)

package com.example.stylefeed.designsystem.components.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.AsyncImage
import kotlin.math.absoluteValue

@Composable
fun ImagePager(
    imageUrls: List<String>,
    pagerState: PagerState,
    imageAspectRatio: Float,
    modifier: Modifier = Modifier,
    onImageClick: (Int) -> Unit = {}
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(imageAspectRatio),
    ) { page ->
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).coerceIn(-1f, 1f)

        AsyncImage(
            model = imageUrls[page],
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = pageOffset * size.width * 0.5f
                    alpha = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                }
                .clickable { onImageClick(page) }
        )
    }
}
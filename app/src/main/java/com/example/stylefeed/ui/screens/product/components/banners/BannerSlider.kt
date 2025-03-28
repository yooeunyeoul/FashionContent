@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.stylefeed.ui.screens.product.components.banners

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stylefeed.designsystem.components.pager.AutoScrollingPager
import com.example.stylefeed.designsystem.components.pager.BannerAutoScroller
import com.example.stylefeed.designsystem.components.pager.PagerIndicator
import com.example.stylefeed.domain.model.Banner
import java.lang.Math.floorMod
import kotlin.math.absoluteValue

@Composable
fun BannerSlider(banners: List<Banner>, isVisible: Boolean, imageAspectRatio: Float) {
    if (banners.isEmpty()) return

    val imageUrls = banners.map { it.thumbnailUrl }
    val pageCount = imageUrls.size
    val virtualCount = pageCount * 1000
    val initialPage = virtualCount / 2 - (virtualCount / 2) % pageCount

    val pagerState = rememberPagerState(initialPage = initialPage) { virtualCount }
    val coroutineScope = rememberCoroutineScope()
    val autoScroller = remember(pagerState, coroutineScope) {
        BannerAutoScroller(pagerState, coroutineScope)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAspectRatio)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)
                    autoScroller.stop()

                    var pointerUp = false
                    while (!pointerUp) {
                        val event = awaitPointerEvent()
                        pointerUp = event.changes.all { !it.pressed }
                    }
                    autoScroller.start()
                }
            }) {

        AutoScrollingPager(
            modifier = Modifier.fillMaxSize(),
            pagerState = pagerState,
            isVisible = isVisible
        ) { virtualPage, pagerState ->
            val realPage = floorMod(virtualPage, pageCount)
            val banner = banners[realPage]

            val pageOffset = (
                    (pagerState.currentPage - virtualPage) + pagerState.currentPageOffsetFraction
                    ).coerceIn(-1f, 1f)

            AsyncImage(
                model = banner.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = pageOffset * size.width * 0.5f
                        alpha = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    }
                    .clickable {

                    }
            )
        }

        PagerIndicator(
            currentPage = floorMod(pagerState.currentPage, pageCount),
            pageCount = pageCount,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        )
    }
}

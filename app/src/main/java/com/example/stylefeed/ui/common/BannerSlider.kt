@file:OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)

package com.example.stylefeed.ui.common

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import com.example.stylefeed.domain.model.Banner
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun BannerSlider(banners: List<Banner>, isVisible: Boolean,imageAspectRatio: Float) {
    if (banners.isEmpty()) return

    val pageCount = banners.size
    val virtualCount = pageCount * 1000
    val initialPage = virtualCount / 2 - (virtualCount / 2) % pageCount
    val pagerState = rememberPagerState(initialPage = initialPage) { virtualCount }

    val coroutineScope = rememberCoroutineScope()
    val autoScrollJob = remember { mutableStateOf<Job?>(null) }

    fun startAutoScroll(delayMillis: Long = 3000L) {
        autoScrollJob.value?.cancel()
        autoScrollJob.value = coroutineScope.launch {
            Log.d("BannerSliderDebug", "AutoScroll will start after $delayMillis ms delay")
            delay(delayMillis)
            while (isActive) {
                Log.d("BannerSliderDebug", "Scrolling to next page")
                pagerState.animateScrollToPage(
                    pagerState.currentPage + 1,
                    animationSpec = tween(100, easing = FastOutSlowInEasing)
                )
                delay(3000)
            }
        }
    }

    LaunchedEffect(isVisible) {
        if (!isVisible && pagerState.currentPageOffsetFraction != 0f) {
            pagerState.animateScrollToPage(
                pagerState.currentPage,  // 현재 페이지로 즉시 되돌리기
                animationSpec = tween(100)  // 매우 짧은 시간으로 빠르게 처리
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current


    fun stopAutoScroll() {
        Log.d("BannerSliderDebug", "AutoScroll stopped")
        autoScrollJob.value?.cancel()
    }

    // Lifecycle에 따라 자동 스크롤 관리
    DisposableEffect(lifecycleOwner.lifecycle, isVisible) {
        val lifecycle = lifecycleOwner.lifecycle

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (isVisible) startAutoScroll()
                }

                Lifecycle.Event.ON_PAUSE -> stopAutoScroll()
                else -> Unit
            }
        }

        lifecycle.addObserver(observer)

        // Lifecycle 및 Visibility에 따라 즉시 상태를 결정합니다.
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && isVisible) {
            startAutoScroll()
        } else {
            stopAutoScroll()
        }

        onDispose {
            lifecycle.removeObserver(observer)
            stopAutoScroll()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAspectRatio)
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    Log.d("BannerSliderDebug", "User pressed (down)")
                    stopAutoScroll()

                    var pointerUp = false
                    while (!pointerUp) {
                        val event = awaitPointerEvent()
                        pointerUp = event.changes.all { !it.pressed }
                    }

                    Log.d("BannerSliderDebug", "User released (up)")
                    startAutoScroll(delayMillis = 3000)
                }
            }) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { virtualPage ->
            val realPage = virtualPage % pageCount
            val banner = banners[realPage]
            val pageOffset =
                ((pagerState.currentPage - virtualPage) + pagerState.currentPageOffsetFraction).coerceIn(
                    -1f,
                    1f
                )

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
                        Log.d("BannerSliderDebug", "Banner clicked: $realPage")
                    }
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${(pagerState.currentPage % pageCount) + 1} / $pageCount",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
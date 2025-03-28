@file:OptIn(ExperimentalFoundationApi::class)

package com.example.stylefeed.designsystem.components.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.stylefeed.designsystem.utils.AutoScroller

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoScrollingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    isVisible: Boolean,
    autoScrollIntervalMillis: Long = 3000L,
    content: @Composable (page: Int, pagerState: PagerState) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val autoScroller = remember(pagerState, coroutineScope) {
        AutoScroller(pagerState, coroutineScope, intervalMillis = autoScrollIntervalMillis)
    }

    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(lifecycleOwner.lifecycle, isVisible) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> if (isVisible) autoScroller.start()
                Lifecycle.Event.ON_PAUSE -> autoScroller.stop()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && isVisible) {
            autoScroller.start()
        } else {
            autoScroller.stop()
        }

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            autoScroller.stop()
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.pointerInput(Unit) {
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
        }
    ) { virtualPage ->
        content(virtualPage, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun AutoScrollingPagerPreview() {
    val pagerState = rememberPagerState(initialPage = 0) { 5 }

    AutoScrollingPager(
        pagerState = pagerState,
        isVisible = true,
        autoScrollIntervalMillis = 3000L,
        modifier = Modifier.fillMaxWidth()
    ) { page, _ ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(
                    when (page % 5) {
                        0 -> Color.Red
                        1 -> Color.Green
                        2 -> Color.Blue
                        3 -> Color.Yellow
                        else -> Color.Magenta
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Page ${(page % 5) + 1}",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

package com.example.stylefeed.designsystem.components.pager

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val AutoScrollIntervalMillis = 3000L
private const val ScrollAnimationDurationMillis = 100

@OptIn(ExperimentalFoundationApi::class)
class BannerAutoScroller(
    private val pagerState: PagerState,
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    fun start(delayMillis: Long = AutoScrollIntervalMillis) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            while (isActive) {
                pagerState.animateScrollToPage(
                    pagerState.currentPage + 1,
                    animationSpec = tween(
                        ScrollAnimationDurationMillis,
                        easing = FastOutSlowInEasing
                    )
                )
                delay(AutoScrollIntervalMillis)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}
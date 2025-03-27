package com.example.stylefeed.designsystem.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class AutoScroller(
    private val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
    private val intervalMillis: Long = 3000L
) {
    private var autoScrollJob: Job? = null

    fun start() {
        if (autoScrollJob?.isActive == true) return

        autoScrollJob = coroutineScope.launch {
            while (true) {
                delay(intervalMillis)
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    fun stop() {
        autoScrollJob?.cancel()
    }
}
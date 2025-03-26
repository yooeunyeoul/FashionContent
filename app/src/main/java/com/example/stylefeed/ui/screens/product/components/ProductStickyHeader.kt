package com.example.stylefeed.ui.screens.product.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private val StickyHeaderHeight = 48.dp
private const val AnimationDurationMillis = 400

@Composable
fun StickyHeader(headerText: String?) {
    val backgroundColor by animateColorAsState(
        targetValue = if (headerText != null) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0f),
        animationSpec = tween(AnimationDurationMillis),
        label = "StickyHeaderBackgroundAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(StickyHeaderHeight)
            .background(backgroundColor)
            .zIndex(1f),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = headerText,
            transitionSpec = {
                (slideInVertically(tween(AnimationDurationMillis)) { height -> height / 3 } + fadeIn(tween(
                    AnimationDurationMillis
                )))
                    .togetherWith(
                        slideOutVertically(tween(AnimationDurationMillis)) { height -> -height / 3 } + fadeOut(tween(
                            AnimationDurationMillis
                        ))
                    )
            },
            label = "StickyHeaderTextAnimation"
        ) { text ->
            text?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
            }
        }
    }
}
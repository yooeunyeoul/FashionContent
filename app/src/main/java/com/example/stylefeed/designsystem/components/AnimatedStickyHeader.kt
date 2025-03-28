package com.example.stylefeed.designsystem.components

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun AnimatedStickyHeader(
    headerText: String?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    headerHeight: Dp = 48.dp,
    horizontalPadding: Dp = 16.dp,
    animationDurationMillis: Int = 400,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (headerText != null) backgroundColor.copy(alpha = 0.9f)
        else backgroundColor.copy(alpha = 0f),
        animationSpec = tween(animationDurationMillis),
        label = "StickyHeaderBackgroundAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(animatedBackgroundColor)
            .zIndex(1f),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = headerText,
            transitionSpec = {
                (slideInVertically(tween(animationDurationMillis)) { it / 3 } + fadeIn(tween(animationDurationMillis)))
                    .togetherWith(
                        slideOutVertically(tween(animationDurationMillis)) { -it / 3 } + fadeOut(tween(animationDurationMillis))
                    )
            },
            label = "StickyHeaderTextAnimation"
        ) { text ->
            text?.let {
                Text(
                    text = it,
                    style = textStyle,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Sticky Header Visible")
@Composable
fun AnimatedStickyHeaderPreview_Visible() {
    MaterialTheme {
        AnimatedStickyHeader(headerText = "지금 인기있는 아이템")
    }
}

@Preview(showBackground = true, name = "Sticky Header Invisible")
@Composable
fun AnimatedStickyHeaderPreview_Invisible() {
    MaterialTheme {
        AnimatedStickyHeader(headerText = null)
    }
}

@Preview(showBackground = true, name = "Sticky Header Custom Colors")
@Composable
fun AnimatedStickyHeaderPreview_CustomColors() {
    MaterialTheme {
        AnimatedStickyHeader(
            headerText = "디스커버리 신상품",
            backgroundColor = Color(0xFF6200EE),
            textColor = Color.White
        )
    }
}

@Preview(showBackground = true, name = "Sticky Header Custom Height")
@Composable
fun AnimatedStickyHeaderPreview_CustomHeight() {
    MaterialTheme {
        AnimatedStickyHeader(
            headerText = "커스텀 높이 헤더",
            headerHeight = 60.dp
        )
    }
}
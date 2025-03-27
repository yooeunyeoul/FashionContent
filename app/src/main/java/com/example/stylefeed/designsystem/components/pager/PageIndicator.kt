package com.example.stylefeed.designsystem.components.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    backgroundColor: Color = Color.Black.copy(alpha = 0.4f),
    contentColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "${currentPage + 1} / $pageCount",
            color = contentColor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, name = "Pager Indicator - Default")
@Composable
fun PagerIndicatorPreviewDefault() {
    MaterialTheme {
        PagerIndicator(
            currentPage = 0,
            pageCount = 5
        )
    }
}

@Preview(showBackground = true, name = "Pager Indicator - Middle Page")
@Composable
fun PagerIndicatorPreviewMiddlePage() {
    MaterialTheme {
        PagerIndicator(
            currentPage = 2,
            pageCount = 5
        )
    }
}

@Preview(showBackground = true, name = "Pager Indicator - Last Page")
@Composable
fun PagerIndicatorPreviewLastPage() {
    MaterialTheme {
        PagerIndicator(
            currentPage = 4,
            pageCount = 5
        )
    }
}

@Preview(showBackground = true, name = "Pager Indicator - Custom Colors")
@Composable
fun PagerIndicatorPreviewCustomColors() {
    MaterialTheme {
        PagerIndicator(
            currentPage = 1,
            pageCount = 3,
            backgroundColor = Color.Blue.copy(alpha = 0.5f),
            contentColor = Color.Yellow
        )
    }
}
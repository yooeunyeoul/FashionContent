package com.example.stylefeed.ui.screens.product.components.footer


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stylefeed.designsystem.components.button.RoundedButton
import com.example.stylefeed.designsystem.theme.FashionContentTheme
import com.example.stylefeed.domain.model.Footer
import com.example.stylefeed.domain.model.FooterType

@Composable
fun ProductFooter(
    modifier: Modifier = Modifier,
    footer: Footer,
    onFooterClick: (FooterType) -> Unit,
    isLoading: Boolean = false,
) {
    RoundedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = footer.title,
        icon = footer.iconUrl?.let { url ->
            {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        },
        enabled = !isLoading,
        loading = isLoading,
        onClick = {
            onFooterClick(footer.type)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProductFooterPreview() {
    FashionContentTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductFooter(
                footer = Footer(
                    type = FooterType.REFRESH,
                    title = "새로고침",
                    iconUrl = "https://example.com/icon.png"
                ),
                onFooterClick = {},
                isLoading = false
            )

            ProductFooter(
                footer = Footer(
                    type = FooterType.MORE,
                    title = "더 보기",
                    iconUrl = null
                ),
                onFooterClick = {},
                isLoading = true
            )
        }
    }
}
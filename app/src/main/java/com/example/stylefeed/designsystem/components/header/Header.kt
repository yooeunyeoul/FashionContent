package com.example.stylefeed.designsystem.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.stylefeed.designsystem.theme.FashionContentTheme


@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    iconUrl: String? = null,
    iconVector: ImageVector? = null,
    linkButtonText: String? = null,
    onLinkClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            when {
                iconVector != null -> {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                iconUrl != null -> {
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        if (linkButtonText != null && onLinkClick != null) {
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = onLinkClick) {
                Text(linkButtonText)
            }
        }
    }
}

@Preview(showBackground = true, name = "Header - Title only")
@Composable
fun HeaderPreviewTitleOnly() {
    FashionContentTheme {
        Header(title = "클리어런스")
    }
}

@Preview(showBackground = true, name = "Header - Title with IconVector")
@Composable
fun HeaderPreviewTitleWithIconVector() {
    FashionContentTheme {
        Header(
            title = "인기 스니커즈",
            iconVector = Icons.Filled.Info
        )
    }
}

@Preview(showBackground = true, name = "Header - Title with Link")
@Composable
fun HeaderPreviewTitleWithLink() {
    FashionContentTheme {
        Header(
            title = "스타일 보기",
            linkButtonText = "전체",
            onLinkClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Header - Title, IconVector, and Link")
@Composable
fun HeaderPreviewAll() {
    FashionContentTheme {
        Header(
            title = "디스커버리 스니커즈",
            iconVector = Icons.Filled.Info,
            linkButtonText = "전체",
            onLinkClick = {}
        )
    }
}
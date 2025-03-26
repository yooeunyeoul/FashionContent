package com.example.stylefeed.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import com.example.stylefeed.domain.model.Header
import com.example.stylefeed.ui.theme.FashionContentTheme

@Composable
fun Header(modifier: Modifier = Modifier, header: Header) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = header.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            header.iconUrl?.let { iconUrl ->
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(iconUrl),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        header.linkUrl?.let {
            TextButton(onClick = { /* navigate */ }) {
                Text("전체")
            }
        }
    }
}

@Composable
fun HeaderPreview(header: Header, iconVector: ImageVector? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = header.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            iconVector?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        header.linkUrl?.let {
            TextButton(onClick = { /* navigate */ }) {
                Text("전체")
            }
        }
    }
}

// 프리뷰: Title Only
@Preview(name = "Title Only")
@Composable
fun HeaderPreview_TitleOnly() {
    FashionContentTheme {
        HeaderPreview(
            header = Header(
                title = "클리어런스",
                iconUrl = null,
                linkUrl = null
            )
        )
    }
}

// 프리뷰: Title with Icon
@Preview(name = "Title with Icon")
@Composable
fun HeaderPreview_TitleWithIcon() {
    FashionContentTheme {
        HeaderPreview(
            header = Header(
                title = "인기 스니커즈",
                iconUrl = null,
                linkUrl = null
            ),
            iconVector = Icons.Filled.Info
        )
    }
}

// 프리뷰: Title with Link
@Preview(name = "Title with Link")
@Composable
fun HeaderPreview_TitleWithLink() {
    FashionContentTheme {
        HeaderPreview(
            header = Header(
                title = "스타일 보기",
                iconUrl = null,
                linkUrl = "https://www.musinsa.com/전체보기"
            )
        )
    }
}

// 프리뷰: Title + Icon + Link
@Preview(name = "Title, Icon and Link")
@Composable
fun HeaderPreview_All() {
    FashionContentTheme {
        HeaderPreview(
            header = Header(
                title = "디스커버리 스니커즈",
                iconUrl = null,
                linkUrl = "https://www.musinsa.com/brands/discoveryexpedition"
            ),
            iconVector = Icons.Filled.Info
        )
    }
}
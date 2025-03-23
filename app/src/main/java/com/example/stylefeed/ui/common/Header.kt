package com.example.stylefeed.ui.common

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.stylefeed.domain.model.Header
import com.example.stylefeed.ui.theme.FashionContentTheme

@Composable
fun Header(header: Header) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = header.title,
            style = MaterialTheme.typography.titleMedium
        )

        // iconURL이 있는 경우 타이틀 오른쪽에 아이콘 표시
        header.iconUrl?.let { iconUrl ->
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // linkURL이 있으면 오른쪽 끝에 전체보기 버튼 표시
        header.linkUrl?.let {
            TextButton(onClick = { /* navigate */ }) {
                Text("전체")
            }
        }
    }
}


// 1. 타이틀만 존재 (iconUrl, linkUrl 없음)
@Preview(name = "Title Only")
@Composable
fun HeaderPreview_TitleOnly() {
    FashionContentTheme {
        Header(
            header = Header(
                title = "클리어런스",
                iconUrl = null,
                linkUrl = null
            )
        )
    }
}

// 2. 타이틀 + 아이콘 존재 (linkUrl 없음)
@Preview(name = "Title with Icon")
@Composable
fun HeaderPreview_TitleWithIcon() {
    FashionContentTheme {
        PreviewHeaderWithMaterialIcon(
            header = Header(
                title = "인기 스니커즈",
                iconUrl = null,
                linkUrl = null
            ),
            icon = Icons.Filled.Info
        )
    }
}

// 3. 타이틀 + 전체버튼 존재 (iconUrl 없음)
@Preview(name = "Title with Link")
@Composable
fun HeaderPreview_TitleWithLink() {
    FashionContentTheme {
        Header(
            header = Header(
                title = "스타일 보기",
                iconUrl = null,
                linkUrl = "https://www.musinsa.com/전체보기"
            )
        )
    }
}

// 4. 타이틀 + 아이콘 + 전체버튼 모두 존재
@Preview(name = "Title, Icon and Link")
@Composable
fun HeaderPreview_All() {
    FashionContentTheme {
        PreviewHeaderWithMaterialIcon(
            header = Header(
                title = "디스커버리 스니커즈",
                iconUrl = null,
                linkUrl = "https://www.musinsa.com/brands/discoveryexpedition"
            ),
            icon = Icons.Filled.Info
        )
    }
}

// 실제 Header 컴포넌트를 Preview 용도로 Wrapping하여 Material 아이콘 사용
@Composable
fun PreviewHeaderWithMaterialIcon(header: Header, icon: ImageVector?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(header.title, style = MaterialTheme.typography.titleMedium)
        icon?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        header.linkUrl?.let {
            TextButton(onClick = { /* navigate */ }) {
                Text("전체")
            }
        }
    }
}
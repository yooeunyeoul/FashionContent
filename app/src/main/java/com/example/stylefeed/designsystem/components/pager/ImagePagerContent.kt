package com.example.stylefeed.designsystem.components.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun ImagePagerContent(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    )
}
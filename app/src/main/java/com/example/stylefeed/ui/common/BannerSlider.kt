@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.stylefeed.ui.common

import coil.compose.AsyncImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.stylefeed.domain.model.Banner

@Composable
fun BannerSlider(banners: List<Banner>) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    HorizontalPager(state = pagerState, modifier = Modifier) { index ->
        val banner = banners[index]
        AsyncImage(
            model = banner.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable { /* navigate */ }
        )
    }
}
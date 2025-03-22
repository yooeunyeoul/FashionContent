package com.example.stylefeed.domain.model

data class Header(
    val title: String,
    val iconUrl: String?,  // 아이콘이 없으면 null
    val linkUrl: String?   // 링크가 없으면 null
)
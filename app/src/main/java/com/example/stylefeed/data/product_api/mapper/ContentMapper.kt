package com.example.stylefeed.data.product_api.mapper

import com.example.stylefeed.data.product_api.dto.ContentsDto
import com.example.stylefeed.domain.Content

fun ContentsDto.toDomain(): Content = when (type?.uppercase()) {
    "BANNER" -> Content.BannerContent(banners.map { it.toDomain() })
    "GRID" -> Content.GridContent(products = goods.map { it.toDomain() })
    "SCROLL" -> Content.ScrollContent(products = goods.map { it.toDomain() })
    "STYLE" -> Content.StyleContent(styles = styles.map { it.toDomain() })
    else -> Content.UnknownContent
}